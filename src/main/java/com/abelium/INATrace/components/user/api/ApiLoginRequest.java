package com.abelium.INATrace.components.user.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiLoginRequest {
	
    @NotNull
    @ApiModelProperty(required = true, value = "Email (username).", position = 0)
	public String username;
	
    @ApiModelProperty(required = true, value = "Password.", position = 1)
	public String password;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
