package com.abelium.inatrace.components.agstack.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "API model for AgStack register field boundary call.")
public class ApiRegisterFieldBoundaryResponse {

	@JsonProperty("Geo Id")
	private String geoID;

	@JsonProperty("matched geo ids")
	private String matchedGeoID;

	public String getGeoID() {
		return geoID;
	}

	public void setGeoID(String geoID) {
		this.geoID = geoID;
	}

	public String getMatchedGeoID() {
		return matchedGeoID;
	}

	public void setMatchedGeoID(String matchedGeoID) {
		this.matchedGeoID = matchedGeoID;
	}

}
