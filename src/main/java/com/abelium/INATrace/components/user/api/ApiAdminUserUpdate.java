package com.abelium.INATrace.components.user.api;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiAdminUserUpdate extends ApiUserUpdate {

	@ApiModelProperty(value = "Name", position = 0)
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
