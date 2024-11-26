package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * REST controller for Final product entity.
 */
@RestController
@RequestMapping("/final-product")
public class FinalProductController {

	private final FinalProductService finalProductService;

	@Autowired
	public FinalProductController(FinalProductService finalProductService) {
		this.finalProductService = finalProductService;
	}

	@GetMapping("company/{companyId}")
	@Operation(summary = "Get a list of final products that the company with the provided ID has access to")
	public ApiPaginatedResponse<ApiFinalProduct> getFinalProductsForCompany(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid ApiPaginatedRequest request,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("companyId") Long companyId) throws ApiException {

		return new ApiPaginatedResponse<>(finalProductService.getFinalProductsForCompany(request, companyId, authUser));
	}
}
