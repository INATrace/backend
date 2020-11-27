package com.abelium.INATrace.components.product.api;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.components.company.api.ApiCompanyListResponse;
import com.abelium.INATrace.types.ProductCompanyType;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductCompany {
	
	@ApiModelProperty(value = "associated company", position = 1)
	@Valid
	public ApiCompanyListResponse company;
	
	@ApiModelProperty(value = "associated company type", position = 2)
	public ProductCompanyType type;

	public ApiCompanyListResponse getCompany() {
		return company;
	}

	public void setCompany(ApiCompanyListResponse company) {
		this.company = company;
	}

	public ProductCompanyType getType() {
		return type;
	}

	public void setType(ProductCompanyType type) {
		this.type = type;
	}
}
