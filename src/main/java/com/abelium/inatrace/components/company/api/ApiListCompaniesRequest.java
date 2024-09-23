package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.CompanyStatus;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListCompaniesRequest extends ApiPaginatedRequest {

	@Parameter(description = "Language")
	public Language language = Language.EN;
	
	@Parameter(description = "Company status, sortable")
	public CompanyStatus status;
	
	@Parameter(description = "Company name (start of name), sortable")
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
