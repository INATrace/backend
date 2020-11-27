package com.abelium.INATrace.components.user.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiResetPasswordRequest {
	
    @NotNull
    @ApiModelProperty(required = true, value = "Reset password token.", position = 0)
	public String token;
	
    @NotNull
    @ApiModelProperty(required = true, value = "Password.", position = 1)
	public String password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
