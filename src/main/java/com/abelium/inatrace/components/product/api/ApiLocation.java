package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.company.api.ApiAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Validated
public class ApiLocation {
	
	@Schema(description = "location address")
	@Valid
	public ApiAddress address;
	
	@Schema(description = "location latitude")
	public Double latitude;
	
	@Schema(description = "location longitude")
	public Double longitude;

	@Schema(description = "number of farmers at this location")
	public Integer numberOfFarmers;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "pin (location) name")
	public String pinName;
	
	public ApiAddress getAddress() {
		return address;
	}

	public void setAddress(ApiAddress address) {
		this.address = address;
	}

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

	public Integer getNumberOfFarmers() {
		return numberOfFarmers;
	}

	public void setNumberOfFarmers(Integer numberOfFarmers) {
		this.numberOfFarmers = numberOfFarmers;
	}

	public String getPinName() {
		return pinName;
	}

	public void setPinName(String pinName) {
		this.pinName = pinName;
	}
}
