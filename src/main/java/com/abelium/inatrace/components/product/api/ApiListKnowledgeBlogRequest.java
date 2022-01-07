package com.abelium.inatrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.KnowledgeBlogType;
import io.swagger.annotations.ApiParam;

@Validated
public class ApiListKnowledgeBlogRequest extends ApiPaginatedRequest {

	@ApiParam(value = "knowledge blog type")
	public KnowledgeBlogType type;

	public KnowledgeBlogType getType() {
		return type;
	}

	public void setType(KnowledgeBlogType type) {
		this.type = type;
	}
}
