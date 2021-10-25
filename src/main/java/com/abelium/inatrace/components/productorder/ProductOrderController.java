package com.abelium.inatrace.components.productorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.productorder.api.ApiProductOrder;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for Product order entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/product-order")
public class ProductOrderController {

	private final ProductOrderService productOrderService;

	@Autowired
	public ProductOrderController(ProductOrderService productOrderService) {
		this.productOrderService = productOrderService;
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single product order with the provided ID.")
	public ApiResponse<ApiProductOrder> getProductOrder(
			@Valid @ApiParam(value = "Product order ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(productOrderService.getProductOrder(id, language));
	}

	@PutMapping
	@ApiOperation("Create or update product order. If the ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProductOrder(
			@Valid @RequestBody ApiProductOrder apiProductOrder) throws ApiException {

		return new ApiResponse<>(productOrderService.createOrUpdateProductOrder(apiProductOrder));
	}

}
