package com.abelium.inatrace.components.product.api;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabel extends ApiProductLabelBase {
	
	@Schema(description = "Fields")
	public List<ApiProductLabelField> fields;

	@Schema(description = "B2C settings")
	private ApiBusinessToCustomerSettings businessToCustomerSettings;

	public List<ApiProductLabelField> getFields() {
		return fields;
	}

	public void setFields(List<ApiProductLabelField> fields) {
		this.fields = fields;
	}

	public ApiBusinessToCustomerSettings getBusinessToCustomerSettings() {
		return businessToCustomerSettings;
	}

	public void setBusinessToCustomerSettings(ApiBusinessToCustomerSettings businessToCustomerSettings) {
		this.businessToCustomerSettings = businessToCustomerSettings;
	}
}
