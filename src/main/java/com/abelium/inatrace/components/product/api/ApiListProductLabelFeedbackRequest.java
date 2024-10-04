package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.ProductLabelFeedbackType;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListProductLabelFeedbackRequest extends ApiPaginatedRequest {

	@Parameter(description = "feedback type")
	public ProductLabelFeedbackType type;

	public ProductLabelFeedbackType getType() {
		return type;
	}

	public void setType(ProductLabelFeedbackType type) {
		this.type = type;
	}
}
