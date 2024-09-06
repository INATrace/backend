package com.abelium.inatrace.components.company.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiCountry;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@Validated
public class ApiAddress {

	@Size(max = Lengths.ADDRESS)
    @ApiModelProperty(value = "address", position = 1)
	public String address;
	
	@Size(max = Lengths.CITY)
    @ApiModelProperty(value = "city", position = 2)
	public String city;

	@Size(max = Lengths.STATE)
    @ApiModelProperty(value = "state / province / region", position = 3)
	public String state;

	@Size(max = Lengths.ZIPCODE)
    @ApiModelProperty(value = "ZIP / postal code / p.p. box", position = 4)
	public String zip;

	@Size(max = Lengths.CELL)
	@ApiModelProperty(value = "Village cell", position = 5)
	public String cell;

	@Size(max = Lengths.SECTOR)
	@ApiModelProperty(value = "Village sector", position = 6)
	public String sector;

	@Size(max = Lengths.VILLAGE)
	@ApiModelProperty(value = "Village name", position = 7)
	public String village;

	@Size(max = Lengths.OTHER_ADDRESS)
	@ApiModelProperty(value = "Other/additional address field", position = 8)
	public String otherAddress;

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Honduras farm name", position = 9)
	public String hondurasFarm;

	@Size(max = Lengths.VILLAGE)
	@ApiModelProperty(value = "Honduras village name", position = 10)
	public String hondurasVillage;

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Honduras municipality name", position = 11)
	public String hondurasMunicipality;

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Honduras department name", position = 12)
	public String hondurasDepartment;

    @ApiModelProperty(value = "country", position = 13)
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

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getOtherAddress() {
		return otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public String getHondurasFarm() {
		return hondurasFarm;
	}

	public void setHondurasFarm(String hondurasFarm) {
		this.hondurasFarm = hondurasFarm;
	}

	public String getHondurasVillage() {
		return hondurasVillage;
	}

	public void setHondurasVillage(String hondurasVillage) {
		this.hondurasVillage = hondurasVillage;
	}

	public String getHondurasMunicipality() {
		return hondurasMunicipality;
	}

	public void setHondurasMunicipality(String hondurasMunicipality) {
		this.hondurasMunicipality = hondurasMunicipality;
	}

	public String getHondurasDepartment() {
		return hondurasDepartment;
	}

	public void setHondurasDepartment(String hondurasDepartment) {
		this.hondurasDepartment = hondurasDepartment;
	}

	public ApiCountry getCountry() {
		return country;
	}

	public void setCountry(ApiCountry country) {
		this.country = country;
	}
}
