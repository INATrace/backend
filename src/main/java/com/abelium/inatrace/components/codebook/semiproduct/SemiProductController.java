package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for semi products.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/semi-product")
public class SemiProductController {

	private final SemiProductService semiProductService;

	@Autowired
	public SemiProductController(SemiProductService semiProductService) {
		this.semiProductService = semiProductService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of semi products.")
	public ApiPaginatedResponse<ApiSemiProduct> getSemiProductList(
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(semiProductService.getSemiProductList(request, language));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single semi product with the provided ID.")
	public ApiResponse<ApiSemiProduct> getSemiProduct(
			@Valid @ApiParam(value = "Semi product ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(semiProductService.getSemiProduct(id, language));
	}

	@GetMapping("{id}/detail")
	@ApiOperation("Get a single semi product with details with the provided ID.")
	public ApiResponse<ApiSemiProduct> getSemiProductDetails(
			@Valid @ApiParam(value = "Semi product ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {
		return new ApiResponse<>(semiProductService.getSemiProductDetails(id, language));
	}

	@PutMapping
	@ApiOperation("Create or update semi product. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateSemiProduct(@Valid @RequestBody ApiSemiProduct apiSemiProduct) throws ApiException {

		return new ApiResponse<>(semiProductService.createOrUpdateSemiProduct(apiSemiProduct));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a semi product with the provided ID.")
	public ApiDefaultResponse deleteSemiProduct(@Valid @ApiParam(value = "Semi product ID", required = true) @PathVariable("id") Long id) throws ApiException {

		semiProductService.deleteSemiProduct(id);
		return new ApiDefaultResponse();
	}

}
