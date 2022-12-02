package com.abelium.inatrace.components.codebook.facility_type;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	@ApiOperation("Get a paginated list of facility types.")
	public ApiPaginatedResponse<ApiFacilityType> getFacilityTypeList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(facilityTypeService.getFacilityTypeList(request));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single facility type with the provided ID.")
	public ApiResponse<ApiFacilityType> getFacilityType(
			@Valid @ApiParam(value = "Facility type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(facilityTypeService.getFacilityType(id));
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Create or update facility type. If ID is provided, the entity entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateFacilityType(
			@Valid @RequestBody ApiFacilityType apiFacilityType) throws ApiException {

		return new ApiResponse<>(facilityTypeService.createOrUpdateFacilityType(apiFacilityType));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Deletes a facility type with the provided ID.")
	public ApiDefaultResponse deleteFacilityType(
			@Valid @ApiParam(value = "Facility type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		facilityTypeService.deleteFacilityType(id);
		return new ApiDefaultResponse();
	}
}
