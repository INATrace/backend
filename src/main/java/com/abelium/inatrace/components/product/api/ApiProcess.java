package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public class ApiProcess {

	@Size(max = 2000)
	@Schema(description = "production description - Briefly describe your production process", maxLength = 2000)
	public String production;
	

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

}
