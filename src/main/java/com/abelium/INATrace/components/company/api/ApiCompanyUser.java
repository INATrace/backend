package com.abelium.INATrace.components.company.api;

import com.abelium.INATrace.components.user.api.ApiUserBase;
import com.abelium.INATrace.types.CompanyUserRole;

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
