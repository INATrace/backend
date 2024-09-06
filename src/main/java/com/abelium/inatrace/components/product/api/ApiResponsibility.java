package com.abelium.inatrace.components.product.api;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

public class ApiResponsibility {
	
	@Size(max = 2000)
	@ApiModelProperty(value = "labor policies - Briefly describe labor policies you have in place in your company", position = 1)
	public String laborPolicies;

	public String getLaborPolicies() {
		return laborPolicies;
	}

	public void setLaborPolicies(String laborPolicies) {
		this.laborPolicies = laborPolicies;
	}

}
