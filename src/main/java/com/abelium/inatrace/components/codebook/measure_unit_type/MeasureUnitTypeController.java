package com.abelium.inatrace.components.codebook.measure_unit_type;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for measure unit type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/measure-unit-type")
public class MeasureUnitTypeController {

	private final MeasureUnitTypeService measureUnitTypeService;

	@Autowired
	public MeasureUnitTypeController(MeasureUnitTypeService measureUnitTypeService) {
		this.measureUnitTypeService = measureUnitTypeService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of measurement types.")
	public ApiPaginatedResponse<ApiMeasureUnitType> getMeasureUnitTypeList(@Valid ApiPaginatedRequest request) {

		return null;
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single measurement unit type with the provided ID.")
	public ApiResponse<ApiMeasureUnitType> getMeasurementUnitType(@Valid @ApiParam(value = "Measurement unit type ID", required = true) @PathVariable("id") Long id) {

		return null;
	}

	@PutMapping
	@ApiOperation("Create or update measurement unit type. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiMeasureUnitType> createOrUpdateMeasurementUnitType(@Valid @RequestBody ApiMeasureUnitType apiMeasureUnitType) {

		return null;
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a measurement with the provided ID.")
	public ApiDefaultResponse deleteMeasurementUnitType(@Valid @ApiParam(value = "Measurement unit type ID", required = true) @PathVariable("id") Long id) {

		return new ApiDefaultResponse();
	}
}
