package com.abelium.inatrace.components.company.api;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiGeoAddress extends ApiAddress {

	@Schema(description = "location latitude")
	public Double latitude;
	
	@Schema(description = "location longitude")
	public Double longitude;


	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
