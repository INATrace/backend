package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.KnowledgeBlogType;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListKnowledgeBlogRequest extends ApiPaginatedRequest {

	@Parameter(description = "knowledge blog type")
	public KnowledgeBlogType type;

	public KnowledgeBlogType getType() {
		return type;
	}

	public void setType(KnowledgeBlogType type) {
		this.type = type;
	}
}
