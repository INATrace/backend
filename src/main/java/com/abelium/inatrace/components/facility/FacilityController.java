package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for facility entity.
 *
 * @author Rene Flores, Pece Adjievki Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/facility")
public class FacilityController {

	private final FacilityService facilityService;

	@Autowired
	public FacilityController(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	@GetMapping("list/company/{id}/all")
	public ApiPaginatedResponse<ApiFacility> listAllFacilitiesByCompany(
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@Valid ApiPaginatedRequest request,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiPaginatedResponse<>(
				facilityService.listAllFacilitiesByCompany(companyId, request, language, authUser));
	}
	
	@GetMapping("list/company/{id}")
	@Operation(summary ="Get a list of facilities by company ID.")
	public ApiPaginatedResponse<ApiFacility> listFacilitiesByCompany(
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @Parameter(description = "Semi product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
			@Valid @Parameter(description = "Final product ID") @RequestParam(value = "finalProductId", required = false) Long finalProductId,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@Valid ApiPaginatedRequest request,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiPaginatedResponse<>(
				facilityService.listFacilitiesByCompany(companyId, semiProductId, finalProductId, request, authUser,
						language));
	}

	@GetMapping("list/company/{id}/available-selling")
	@Operation(summary ="Get a list of public (selling) facilities that the provided company can see")
	public ApiPaginatedResponse<ApiFacility> listAvailableSellingFacilitiesForCompany(
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @Parameter(description = "Semi product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
			@Valid @Parameter(description = "Final product ID") @RequestParam(value = "finalProductId", required = false) Long finalProductId,
			@Valid ApiPaginatedRequest request,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiPaginatedResponse<>(
				facilityService.listAvailableSellingFacilitiesForCompany(companyId, semiProductId, finalProductId,
						request, authUser, language));
	}
	
	@GetMapping("list/collecting/company/{id}")
	@Operation(summary ="Get a list of collecting facilities by company ID.")
	public ApiPaginatedResponse<ApiFacility> listCollectingFacilitiesByCompany(
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@Valid ApiPaginatedRequest request,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiPaginatedResponse<>(facilityService.listCollectingFacilitiesByCompany(companyId, request, authUser, language));
	}

	@GetMapping("{id}")
	@Operation(summary ="Get a single facility with the provided ID.")
	public ApiResponse<ApiFacility> getFacility(
			@Valid @Parameter(description = "Facility ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiResponse<>(facilityService.getFacility(id, authUser, language));

	}

	@GetMapping("{id}/detail")
	@Operation(summary ="Get a single facility with translations for the provided ID.")
	public ApiResponse<ApiFacility> getFacilityDetail(
			@Valid @Parameter(description = "Facility ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiResponse<>(facilityService.getFacilityDetail(id, authUser, language));
	}

	@PutMapping
	@Operation(summary ="Create or update facility. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateFacility(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiFacility apiFacility) throws ApiException {

		return new ApiResponse<>(facilityService.createOrUpdateFacility(apiFacility, authUser));
	}

	@PutMapping("{id}/activate")
	@Operation(summary ="Activate a facility")
	public ApiDefaultResponse activateFacility(@Valid @Parameter(description = "Facility ID", required = true) @PathVariable("id") Long id,
											   @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {
		facilityService.setFacilityDeactivatedStatus(id, Boolean.FALSE, authUser);
		return new ApiDefaultResponse();
	}

	@PutMapping("{id}/deactivate")
	@Operation(summary ="Deactivate a facility")
	public ApiDefaultResponse deactivateFacility(@Valid @Parameter(description = "Facility ID", required = true) @PathVariable("id") Long id,
												 @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {
		facilityService.setFacilityDeactivatedStatus(id, Boolean.TRUE, authUser);
		return new ApiDefaultResponse();
	}

	@DeleteMapping("{id}")
	@Operation(summary ="Deletes a facility with the provided ID.")
	public ApiDefaultResponse deleteFacility(@Valid @Parameter(description = "Facility ID", required = true) @PathVariable("id") Long id,
											 @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		facilityService.deleteFacility(id, authUser);
		return new ApiDefaultResponse();

	}

}
