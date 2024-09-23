package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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
	@Operation(summary ="Get a paginated list of semi products.")
	public ApiPaginatedResponse<ApiSemiProduct> getSemiProductList(
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(semiProductService.getSemiProductList(request, language));
	}

	@GetMapping("list/by-value-chains")
	@Operation(summary ="Get a paginated list of semi products for given value-chain list")
	public ApiPaginatedResponse<ApiSemiProduct> getSemiProductListByValueChains(
			@Parameter(description = "Value chain IDs", required = true) @RequestParam(value = "valueChainIds") List<Long> valueChainIds,
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(semiProductService.getSemiProductListByValueChains(request, valueChainIds, language));
	}

	@GetMapping("{id}")
	@Operation(summary ="Get a single semi product with the provided ID.")
	public ApiResponse<ApiSemiProduct> getSemiProduct(
			@Valid @Parameter(description = "Semi product ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(semiProductService.getSemiProduct(id, language));
	}

	@GetMapping("{id}/detail")
	@Operation(summary ="Get a single semi product with details with the provided ID.")
	public ApiResponse<ApiSemiProduct> getSemiProductDetails(
			@Valid @Parameter(description = "Semi product ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {
		return new ApiResponse<>(semiProductService.getSemiProductDetails(id, language));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'REGIONAL_ADMIN')")
	@Operation(summary ="Create or update semi product. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateSemiProduct(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiSemiProduct apiSemiProduct) throws ApiException {

		return new ApiResponse<>(semiProductService.createOrUpdateSemiProduct(authUser, apiSemiProduct));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Deletes a semi product with the provided ID.")
	public ApiDefaultResponse deleteSemiProduct(@Valid @Parameter(description = "Semi product ID", required = true) @PathVariable("id") Long id) throws ApiException {

		semiProductService.deleteSemiProduct(id);
		return new ApiDefaultResponse();
	}

}
