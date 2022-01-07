package com.abelium.inatrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.ProductLabelFeedbackType;

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
