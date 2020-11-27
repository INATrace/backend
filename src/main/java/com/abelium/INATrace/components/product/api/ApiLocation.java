package com.abelium.INATrace.components.product.api;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.components.company.api.ApiAddress;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiLocation {
	
	@ApiModelProperty(value = "location address", position = 1)
	@Valid
	public ApiAddress address;
	
	@ApiModelProperty(value = "location latitude", position = 2)
	public Double latitude;
	
	@ApiModelProperty(value = "location longitude", position = 3)
	public Double longitude;

	@ApiModelProperty(value = "number of farmers at this location", position = 4)
	public Integer numberOfFarmers;

	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "pin (location) name", position = 5)
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
