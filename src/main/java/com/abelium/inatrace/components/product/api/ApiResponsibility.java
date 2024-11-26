package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

public class ApiResponsibility {
	
	@Size(max = 2000)
	@Schema(description = "labor policies - Briefly describe labor policies you have in place in your company", maxLength = 2000)
	public String laborPolicies;

	public String getLaborPolicies() {
		return laborPolicies;
	}

	public void setLaborPolicies(String laborPolicies) {
		this.laborPolicies = laborPolicies;
	}

}
