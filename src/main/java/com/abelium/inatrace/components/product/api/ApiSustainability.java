package com.abelium.inatrace.components.product.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiSustainability {
	
	@Length(max = 1000)
	@ApiModelProperty(value = "environmentally friendly production, max 1000 chars", position = 1)
	public String production;

	@Length(max = 1000)
	@ApiModelProperty(value = "sustainable packaging - Describe the environmental sustainability of your packaging, max 1000 chars", position = 2)
	public String packaging;
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "CO2 footprint - If you have calculated your company CO2 footprint, please add this information", position = 3)
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
