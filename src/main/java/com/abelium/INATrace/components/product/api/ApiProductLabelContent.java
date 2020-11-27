package com.abelium.INATrace.components.product.api;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.components.company.api.ApiCompany;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelContent extends ApiProductContent {

	@ApiModelProperty(value = "company", position = 14)
	@Valid
	public ApiCompany company;
	
	@ApiModelProperty(value = "label id", position = 21)
	public Long labelId;
	

	public ApiCompany getCompany() {
		return company;
	}

	public void setCompany(ApiCompany company) {
		this.company = company;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

}
