package com.abelium.inatrace.components.codebook.grade_abbreviation;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for grade abbreviation entity.
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

		return null;
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single grade abbreviation with the provided ID.")
	public ApiResponse<ApiGradeAbbreviation> getGradeAbbreviation(@Valid @ApiParam(value = "Grade abbreviation ID", required = true) @PathVariable("id") Long id) {

		return null;
	}

	@PutMapping
	@ApiOperation("Create or update grade abbreviation. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiGradeAbbreviation> createOrUpdateGradeAbbreviation(@Valid @RequestBody ApiGradeAbbreviation apiGradeAbbreviation) {

		return null;
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a grade abbreviation with the provided ID.")
	public ApiDefaultResponse deleteGradeAbbreviation(@Valid @ApiParam(value = "Grade abbreviation ID", required = true) @PathVariable("id") Long id) {

		return new ApiDefaultResponse();
	}

}
