package com.abelium.inatrace.components.company.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiCompanyName extends ApiCompanyBase {

	@Schema(description = "company name")
	public String name;

	@Schema(description = "company abbreviation")
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
