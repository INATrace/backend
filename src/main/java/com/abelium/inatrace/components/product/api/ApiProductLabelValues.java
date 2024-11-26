package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ApiProductLabelValues extends ApiProductLabelBase {

	@Schema(description = "Fields")
	public List<ApiProductLabelFieldValue> fields;

	public List<ApiProductLabelFieldValue> getFields() {
		return fields;
	}

	public void setFields(List<ApiProductLabelFieldValue> fields) {
		this.fields = fields;
	}
}
