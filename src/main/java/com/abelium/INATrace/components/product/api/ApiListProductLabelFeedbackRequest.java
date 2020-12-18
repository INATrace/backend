package com.abelium.INATrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiPaginatedRequest;
import com.abelium.INATrace.types.ProductLabelFeedbackType;

import io.swagger.annotations.ApiParam;

@Validated
public class ApiListProductLabelFeedbackRequest extends ApiPaginatedRequest {

	@ApiParam(value = "feedback type")
	public ProductLabelFeedbackType type;

	public ProductLabelFeedbackType getType() {
		return type;
	}

	public void setType(ProductLabelFeedbackType type) {
		this.type = type;
	}
}
