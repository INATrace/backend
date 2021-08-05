package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.user.api.ApiUserBase;
import com.abelium.inatrace.types.CompanyUserRole;

import io.swagger.annotations.ApiModelProperty;

public class ApiCompanyUser extends ApiUserBase {

	
	@ApiModelProperty(value = "company role")
	public CompanyUserRole companyRole;

	public CompanyUserRole getCompanyRole() {
		return companyRole;
	}

	public void setCompanyRole(CompanyUserRole companyRole) {
		this.companyRole = companyRole;
	}
}
