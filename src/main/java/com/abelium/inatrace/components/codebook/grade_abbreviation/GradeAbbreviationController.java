package com.abelium.inatrace.components.codebook.grade_abbreviation;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for grade abbreviation entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/grade-abbreviation")
public class GradeAbbreviationController {

	private final GradeAbbreviationService gradeAbbreviationService;

	@Autowired
	public GradeAbbreviationController(GradeAbbreviationService gradeAbbreviationService) {
		this.gradeAbbreviationService = gradeAbbreviationService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of grade abbreviations.")
	public ApiPaginatedResponse<ApiGradeAbbreviation> getGradeAbbreviationList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(gradeAbbreviationService.getGradeAbbreviationList(request));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single grade abbreviation with the provided ID.")
	public ApiResponse<ApiGradeAbbreviation> getGradeAbbreviation(
			@Valid @ApiParam(value = "Grade abbreviation ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(gradeAbbreviationService.getGradeAbbreviation(id));
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Create or update grade abbreviation. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateGradeAbbreviation(
			@Valid @RequestBody ApiGradeAbbreviation apiGradeAbbreviation) throws ApiException {

		return new ApiResponse<>(gradeAbbreviationService.createOrUpdateGradeAbbreviation(apiGradeAbbreviation));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Deletes a grade abbreviation with the provided ID.")
	public ApiDefaultResponse deleteGradeAbbreviation(
			@Valid @ApiParam(value = "Grade abbreviation ID", required = true) @PathVariable("id") Long id) throws ApiException {

		gradeAbbreviationService.deleteGradeAbbreviation(id);
		return new ApiDefaultResponse();
	}

}
