package com.abelium.inatrace.components.user.api;

import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.components.user.types.UserAction;

import io.swagger.annotations.ApiModelProperty;

public class ApiUserGet extends ApiUser {
	
	@ApiModelProperty("Possible actions")
	public List<UserAction> actions = new ArrayList<>();

	@ApiModelProperty("User's company ids")
	public List<Long> companyIds = new ArrayList<>();

	@ApiModelProperty("User's company ids where user is company admin")
	public List<Long> companyIdsAdmin = new ArrayList<>();
	
	public List<UserAction> getActions() {
		return actions;
	}

	public void setActions(List<UserAction> actions) {
		this.actions = actions;
	}

	public List<Long> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Long> companyIds) {
		this.companyIds = companyIds;
	}

	public List<Long> getCompanyIdsAdmin() {
		return companyIdsAdmin;
	}

	public void setCompanyIdsAdmin(List<Long> companyIdsAdmin) {
		this.companyIdsAdmin = companyIdsAdmin;
	}
}
