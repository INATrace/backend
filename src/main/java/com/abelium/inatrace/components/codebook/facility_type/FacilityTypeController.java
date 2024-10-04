package com.abelium.inatrace.components.codebook.facility_type;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for facility type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/facility-type")
public class FacilityTypeController {

	private final FacilityTypeService facilityTypeService;

	@Autowired
	public FacilityTypeController(FacilityTypeService facilityTypeService) {
		this.facilityTypeService = facilityTypeService;
	}

	@GetMapping("list")
	@Operation(summary ="Get a paginated list of facility types.")
	public ApiPaginatedResponse<ApiFacilityType> getFacilityTypeList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(facilityTypeService.getFacilityTypeList(request));
	}

	@GetMapping("{id}")
	@Operation(summary ="Get a single facility type with the provided ID.")
	public ApiResponse<ApiFacilityType> getFacilityType(
			@Valid @Parameter(description = "Facility type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(facilityTypeService.getFacilityType(id));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'REGIONAL_ADMIN')")
	@Operation(summary ="Create or update facility type. If ID is provided, the entity entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateFacilityType(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiFacilityType apiFacilityType) throws ApiException {

		return new ApiResponse<>(facilityTypeService.createOrUpdateFacilityType(authUser, apiFacilityType));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Deletes a facility type with the provided ID.")
	public ApiDefaultResponse deleteFacilityType(
			@Valid @Parameter(description = "Facility type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		facilityTypeService.deleteFacilityType(id);
		return new ApiDefaultResponse();
	}
}
