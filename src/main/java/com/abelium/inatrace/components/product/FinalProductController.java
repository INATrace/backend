package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for Final prodcut entity.
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
	@ApiOperation("Get a list of final products that the company with the provided ID has access to")
	public ApiPaginatedResponse<ApiFinalProduct> getFinalProductsForCompany(
			@Valid ApiPaginatedRequest request,
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId) {

		return new ApiPaginatedResponse<>(finalProductService.getFinalProductsForCompany(request, companyId));
	}
}
