package com.abelium.inatrace.components.codebook.measure_unit_type;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
	@Operation(summary ="Get a paginated list of measurement types.")
	public ApiPaginatedResponse<ApiMeasureUnitType> getMeasureUnitTypeList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(measureUnitTypeService.getMeasureUnitTypeList(request));
	}

	@GetMapping("{id}")
	@Operation(summary ="Get a single measurement unit type with the provided ID.")
	public ApiResponse<ApiMeasureUnitType> getMeasurementUnitType(
			@Valid @Parameter(description = "Measurement unit type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(measureUnitTypeService.getMeasureUnitType(id));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'REGIONAL_ADMIN')")
	@Operation(summary ="Create or update measurement unit type. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateMeasurementUnitType(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiMeasureUnitType apiMeasureUnitType) throws ApiException {

		return new ApiResponse<>(measureUnitTypeService.createOrUpdateMeasureUnitType(authUser, apiMeasureUnitType));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Deletes a measurement with the provided ID.")
	public ApiDefaultResponse deleteMeasurementUnitType(
			@Valid @Parameter(description = "Measurement unit type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		measureUnitTypeService.deleteMeasureUnitType(id);
		return new ApiDefaultResponse();
	}
}
