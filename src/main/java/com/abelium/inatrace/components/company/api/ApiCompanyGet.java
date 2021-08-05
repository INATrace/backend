package com.abelium.inatrace.components.company.api;

import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.components.company.types.CompanyAction;
import io.swagger.annotations.ApiModelProperty;

public class ApiCompanyGet extends ApiCompany {

	@ApiModelProperty("Possible actions")
	public List<CompanyAction> actions = new ArrayList<>();

	@ApiModelProperty("Company users")
	public List<ApiCompanyUser> users = new ArrayList<>();

	public List<CompanyAction> getActions() {
		return actions;
	}

	public void setActions(List<CompanyAction> actions) {
		this.actions = actions;
	}

	public List<ApiCompanyUser> getUsers() {
		return users;
	}

	public void setUsers(List<ApiCompanyUser> users) {
		this.users = users;
	}

}
