package com.abelium.inatrace.components.company.api;

import io.swagger.annotations.ApiModelProperty;

public class ApiCompanyName extends ApiCompanyBase {

	@ApiModelProperty(value = "company name", position = 2)
	public String name;

	@ApiModelProperty(value = "company abbreviation", position = 3)
	public String abbreviation;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAbbreviation() {
		return abbreviation;
	}

	@Override
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
