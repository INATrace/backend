package com.abelium.INATrace.components.product.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.components.company.api.ApiCompany;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProduct extends ApiProductContent {

	@ApiModelProperty(value = "company", position = 14)
	@Valid
	public ApiCompany company;
	
	@ApiModelProperty(value = "associated companies", position = 14)
	@Valid
	public List<ApiProductCompany> associatedCompanies;
	
	@ApiModelProperty(value = "labels", position = 15)
	public List<ApiProductLabelValues> labels;

	
	public List<ApiProductCompany> getAssociatedCompanies() {
		return associatedCompanies;
	}

	public void setAssociatedCompanies(List<ApiProductCompany> associatedCompanies) {
		this.associatedCompanies = associatedCompanies;
	}

	public List<ApiProductLabelValues> getLabels() {
		return labels;
	}

	public void setLabels(List<ApiProductLabelValues> labels) {
		this.labels = labels;
	}

}
