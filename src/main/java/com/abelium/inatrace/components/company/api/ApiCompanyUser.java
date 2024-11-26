package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.user.api.ApiUserBase;
import com.abelium.inatrace.types.CompanyUserRole;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiCompanyUser extends ApiUserBase {

	@Schema(description = "company role")
	public CompanyUserRole companyRole;

	public CompanyUserRole getCompanyRole() {
		return companyRole;
	}

	public void setCompanyRole(CompanyUserRole companyRole) {
		this.companyRole = companyRole;
	}

}
