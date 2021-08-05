package com.abelium.inatrace.components.company.api;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiGeoAddress extends ApiAddress {

	@ApiModelProperty(value = "location latitude", position = 6)
	public Double latitude;
	
	@ApiModelProperty(value = "location longitude", position = 7)
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
