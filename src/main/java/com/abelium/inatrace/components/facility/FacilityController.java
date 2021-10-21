package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/facility")
public class FacilityController {

	private final FacilityService facilityService;

	@Autowired
	public FacilityController(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of facilities.")
	public ApiPaginatedResponse<ApiFacility> getFacilityList(
			@Valid @ApiParam(value = "Language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language,
			@Valid ApiPaginatedRequest request) {
		return new ApiPaginatedResponse<>(facilityService.getFacilityList(request, Language.valueOf(language)));
	}
	
	@GetMapping("list/company/{id}")
	@ApiOperation("Get a list of facilities by company ID.")
	public ApiPaginatedResponse<ApiFacility> listFacilitiesByCompany(
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @ApiParam(value = "Semi product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
			@Valid @ApiParam(value = "Language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language,
			@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(facilityService.listFacilitiesByCompany(companyId, semiProductId, request, Language.valueOf(language)));
	}
	
	@GetMapping("list/collecting/company/{id}")
	@ApiOperation("Get a list of collecting facilities by company ID.")
	public ApiPaginatedResponse<ApiFacility> listCollectingFacilitiesByCompany(
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @ApiParam(value = "Language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language,
			@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(facilityService.listCollectingFacilitiesByCompany(companyId, request, Language.valueOf(language)));
	}

	@GetMapping("list/selling/company/{id}")
	@ApiOperation("Get a list of selling facilities by company ID.")
	public ApiPaginatedResponse<ApiFacility> listSellingFacilitiesByCompany(
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @ApiParam(value = "Semi product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
			@Valid @ApiParam(value = "Language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language,
			@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(facilityService.listSellingFacilitiesByCompany(companyId, semiProductId, request, Language.valueOf(language)));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single facility with the provided ID.")
	public ApiResponse<ApiFacility> getFacility(@Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(facilityService.getFacility(id));

	}

	@GetMapping("{id}/detail")
	@ApiOperation("Get a single facility with translations for the provided ID.")
	public ApiResponse<ApiFacility> getFacilityDetail(
			@Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("id") Long id) throws ApiException {
		return new ApiResponse<>(facilityService.getFacilityDetail(id));
	}

	@PutMapping
	@ApiOperation("Create or update facility. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateFacility(@Valid @RequestBody ApiFacility apiFacility) throws ApiException {

		return new ApiResponse<>(facilityService.createOrUpdateFacility(apiFacility));

	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a facility with the provided ID.")
	public ApiDefaultResponse deleteFacility(@Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("id") Long id) throws ApiException {

		facilityService.deleteFacility(id);
		return new ApiDefaultResponse();

	}
}
