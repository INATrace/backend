package com.abelium.inatrace.components.agstack.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API model for AgStack register field boundary request.")
public class ApiRegisterFieldBoundaryRequest {

	@JsonProperty("s2_index")
	private String s2Index;

	private String wkt;

	public String getS2Index() {
		return s2Index;
	}

	public void setS2Index(String s2Index) {
		this.s2Index = s2Index;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

}
