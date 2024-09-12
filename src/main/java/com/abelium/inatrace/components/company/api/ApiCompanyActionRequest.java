package com.abelium.inatrace.components.company.api;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.types.CompanyUserRole;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiCompanyActionRequest {
	
	@NotNull
	@Schema(description = "Company id")
	public Long companyId;
	
	@Schema(description = "User id")
	public Long userId;

	@Schema(description = "Other company id")
	public Long otherCompanyId;
	
	@Schema(description = "Company user role")
	public CompanyUserRole companyUserRole = CompanyUserRole.COMPANY_USER;
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOtherCompanyId() {
		return otherCompanyId;
	}

	public void setOtherCompanyId(Long otherCompanyId) {
		this.otherCompanyId = otherCompanyId;
	}

	public CompanyUserRole getCompanyUserRole() {
		return companyUserRole;
	}

	public void setCompanyUserRole(CompanyUserRole companyUserRole) {
		this.companyUserRole = companyUserRole;
	}
}
