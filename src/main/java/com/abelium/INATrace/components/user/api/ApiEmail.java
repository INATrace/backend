package com.abelium.INATrace.components.user.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiEmail {
	
	@NotNull
	@ApiModelProperty(value = "Email", position = 0)
	public String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
