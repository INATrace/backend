package com.abelium.inatrace.components.product.api;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.components.company.api.ApiCompanyListResponse;
import com.abelium.inatrace.types.ProductCompanyType;

@Validated
public class ApiProductCompany {

	@Valid
	@Schema(description = "associated company")
	public ApiCompanyListResponse company;
	
	@Schema(description = "associated company type")
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
