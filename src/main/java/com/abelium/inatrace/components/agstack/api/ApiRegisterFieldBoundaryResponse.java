package com.abelium.inatrace.components.agstack.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "API model for AgStack register field boundary call.")
public class ApiRegisterFieldBoundaryResponse {

	@JsonProperty("Geo Id")
	private String geoID;

	@JsonProperty("matched geo ids")
	private List<String> matchedGeoIDs;

	public String getGeoID() {
		return geoID;
	}

	public void setGeoID(String geoID) {
		this.geoID = geoID;
	}

	public List<String> getMatchedGeoIDs() {
		return matchedGeoIDs;
	}

	public void setMatchedGeoIDs(List<String> matchedGeoIDs) {
		this.matchedGeoIDs = matchedGeoIDs;
	}

}
