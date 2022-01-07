package com.abelium.inatrace.components.user.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiToken {
	
	@NotNull
	@ApiModelProperty(value = "Entity id", position = 0)
	public String token;

	public String getToken() {
		return token;
	}

	public void settoken(String token) {
		this.token = token;
	}
}
