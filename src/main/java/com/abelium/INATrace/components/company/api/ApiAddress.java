package com.abelium.INATrace.components.company.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.components.common.api.ApiCountry;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiAddress {

	@Length(max = Lengths.ADDRESS)
    @ApiModelProperty(value = "address", position = 1)
	public String address;
	
	@Length(max = Lengths.CITY)
    @ApiModelProperty(value = "city", position = 2)
	public String city;

	@Length(max = Lengths.STATE)
    @ApiModelProperty(value = "state / province / region", position = 3)
	public String state;

	@Length(max = Lengths.ZIPCODE)
    @ApiModelProperty(value = "ZIP / postal code / p.p. box", position = 4)
	public String zip;
	
    @ApiModelProperty(value = "country", position = 5)
	public ApiCountry country;


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public ApiCountry getCountry() {
		return country;
	}

	public void setCountry(ApiCountry country) {
		this.country = country;
	}
}
