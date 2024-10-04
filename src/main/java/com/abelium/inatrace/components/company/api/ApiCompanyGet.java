package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.types.ProductCompanyType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class ApiCompanyGet extends ApiCompany {

	@Schema(description = "Possible actions")
	public List<CompanyAction> actions = new ArrayList<>();

	@Schema(description = "Company users")
	public List<ApiCompanyUser> users = new ArrayList<>();

	@Schema(description = "Roles by which this company is connected in the Products where it is part of")
	public List<ProductCompanyType> companyRoles;

	@Schema(description = "Flag indicating that the company supports collectors for deliveries")
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
