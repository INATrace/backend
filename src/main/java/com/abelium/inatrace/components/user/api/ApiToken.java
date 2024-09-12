package com.abelium.inatrace.components.user.api;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiToken {
	
	@NotNull
	@Schema(description = "Entity id")
	public String token;

	public String getToken() {
		return token;
	}

	public void settoken(String token) {
		this.token = token;
	}
}
