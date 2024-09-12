package com.abelium.inatrace.components.user.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiAdminUserUpdate extends ApiUserUpdate {

	@Schema(description = "Name")
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
