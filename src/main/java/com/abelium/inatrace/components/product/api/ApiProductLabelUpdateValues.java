package com.abelium.inatrace.components.product.api;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabelUpdateValues extends ApiProductLabelBase {
	
	@NotNull
	@Schema(description = "Fields")
	public List<ApiProductLabelFieldValue> fields;

	public List<ApiProductLabelFieldValue> getFields() {
		return fields;
	}

	public void setFields(List<ApiProductLabelFieldValue> fields) {
		this.fields = fields;
	}
}
