package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

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

	@ApiModelProperty(value = "valueChain")
	public ApiValueChain valueChain;

	public ApiCompany getCompany() {
		return company;
	}

	public void setCompany(ApiCompany company) {
		this.company = company;
	}

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

	public ApiValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ApiValueChain valueChain) {
		this.valueChain = valueChain;
	}
}
