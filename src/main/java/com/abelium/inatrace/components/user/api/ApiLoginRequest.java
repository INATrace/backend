package com.abelium.inatrace.components.user.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
public class ApiLoginRequest {
	
    @NotNull
    @Schema(description = "Email (username).", requiredMode = Schema.RequiredMode.REQUIRED)
	public String username;
	
    @Schema(description = "Password.")
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
