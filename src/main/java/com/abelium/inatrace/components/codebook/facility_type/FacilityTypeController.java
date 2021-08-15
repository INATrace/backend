package com.abelium.inatrace.components.codebook.facility_type;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

		return null;
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single facility type with the provided ID.")
	public ApiResponse<ApiFacilityType> getFacilityType(@Valid @ApiParam(value = "Facility type ID", required = true) @PathVariable("id") Long id) {

		return null;
	}

	@PutMapping
	@ApiOperation("Create or update facility type. If ID is provided, the entity entity with the provided ID is updated.")
	public ApiResponse<ApiFacilityType> createOrUpdateFacilityType(@Valid @RequestBody ApiFacilityType apiFacilityType) {

		return null;
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a facility type with the provided ID.")
	public ApiDefaultResponse deleteFacilityType(@Valid @ApiParam(value = "Facility type ID", required = true) @PathVariable("id") Long id) {

		return new ApiDefaultResponse();
	}
}
