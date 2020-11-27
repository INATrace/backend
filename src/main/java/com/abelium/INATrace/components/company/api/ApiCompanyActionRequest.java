package com.abelium.INATrace.components.company.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCompanyActionRequest {
	
	@NotNull
	@ApiModelProperty(value = "Company id", position = 0)
	public Long companyId;
	
	@ApiModelProperty(value = "User id", position = 1)
	public Long userId;

	@ApiModelProperty(value = "Other company id", position = 2)
	public Long otherCompanyId;

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
}
