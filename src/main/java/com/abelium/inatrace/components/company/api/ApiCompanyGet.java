package com.abelium.inatrace.components.company.api;

import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.types.ProductCompanyType;
import io.swagger.annotations.ApiModelProperty;

public class ApiCompanyGet extends ApiCompany {

	@ApiModelProperty("Possible actions")
	public List<CompanyAction> actions = new ArrayList<>();

	@ApiModelProperty("Company users")
	public List<ApiCompanyUser> users = new ArrayList<>();

	@ApiModelProperty("Roles by which this company is connected in the Products where it is part of")
	public List<ProductCompanyType> companyRoles;

	@ApiModelProperty("Flag indicating that the company supports collectors for deliveries")
	public Boolean supportsCollectors;

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

	public List<ProductCompanyType> getCompanyRoles() {
		return companyRoles;
	}

	public void setCompanyRoles(List<ProductCompanyType> companyRoles) {
		this.companyRoles = companyRoles;
	}

	public Boolean getSupportsCollectors() {
		return supportsCollectors;
	}

	public void setSupportsCollectors(Boolean supportsCollectors) {
		this.supportsCollectors = supportsCollectors;
	}
}
