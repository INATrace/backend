package com.abelium.INATrace.components.product.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelUpdateValues extends ApiProductLabelBase {
	
	@NotNull
	@ApiModelProperty(value = "Fields", position = 5)
	public List<ApiProductLabelFieldValue> fields;

	public List<ApiProductLabelFieldValue> getFields() {
		return fields;
	}

	public void setFields(List<ApiProductLabelFieldValue> fields) {
		this.fields = fields;
	}
}
