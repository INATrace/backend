package com.abelium.inatrace.components.company.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.CompanyStatus;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiParam;

@Validated
public class ApiListCompaniesRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Language")
	public Language language = Language.EN;
	
	@ApiParam(value = "Company status, sortable")
	public CompanyStatus status;
	
	@ApiParam(value = "Company name (start of name), sortable")
	public String name;
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
