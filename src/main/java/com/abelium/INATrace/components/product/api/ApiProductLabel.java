package com.abelium.INATrace.components.product.api;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabel extends ApiProductLabelBase {
	
	@ApiModelProperty(value = "Fields", position = 6)
	public List<ApiProductLabelField> fields;

	public List<ApiProductLabelField> getFields() {
		return fields;
	}

	public void setFields(List<ApiProductLabelField> fields) {
		this.fields = fields;
	}
}
