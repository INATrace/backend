package com.abelium.inatrace.components.user.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
public class ApiResetPasswordRequest {
	
    @NotNull
    @Schema(description = "Reset password token.", requiredMode = Schema.RequiredMode.REQUIRED)
	public String token;
	
    @NotNull
    @Schema(description = "Password.", requiredMode = Schema.RequiredMode.REQUIRED)
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
