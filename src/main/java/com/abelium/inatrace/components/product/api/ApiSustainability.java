package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public class ApiSustainability {
	
	@Size(max = 1000)
	@Schema(description = "environmentally friendly production, max 1000 chars", maxLength = 1000)
	public String production;

	@Size(max = 1000)
	@Schema(description = "sustainable packaging - Describe the environmental sustainability of your packaging, max 1000 chars", maxLength = 1000)
	public String packaging;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "CO2 footprint - If you have calculated your company CO2 footprint, please add this information", maxLength = Lengths.DEFAULT)
	public String co2Footprint;

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getCo2Footprint() {
		return co2Footprint;
	}

	public void setCo2Footprint(String co2Footprint) {
		this.co2Footprint = co2Footprint;
	}
}
