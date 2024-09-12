package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiCountry;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public class ApiAddress {

	@Size(max = Lengths.ADDRESS)
    @Schema(description = "address", maxLength = Lengths.ADDRESS)
	public String address;
	
	@Size(max = Lengths.CITY)
    @Schema(description = "city", maxLength = Lengths.CITY)
	public String city;

	@Size(max = Lengths.STATE)
    @Schema(description = "state / province / region", maxLength = Lengths.STATE)
	public String state;

	@Size(max = Lengths.ZIPCODE)
    @Schema(description = "ZIP / postal code / p.p. box", maxLength = Lengths.ZIPCODE)
	public String zip;

	@Size(max = Lengths.CELL)
	@Schema(description = "Village cell", maxLength = Lengths.CELL)
	public String cell;

	@Size(max = Lengths.SECTOR)
	@Schema(description = "Village sector", maxLength = Lengths.SECTOR)
	public String sector;

	@Size(max = Lengths.VILLAGE)
	@Schema(description = "Village name", maxLength = Lengths.VILLAGE)
	public String village;

	@Size(max = Lengths.OTHER_ADDRESS)
	@Schema(description = "Other/additional address field", maxLength = Lengths.OTHER_ADDRESS)
	public String otherAddress;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Honduras farm name", maxLength = Lengths.DEFAULT)
	public String hondurasFarm;

	@Size(max = Lengths.VILLAGE)
	@Schema(description = "Honduras village name", maxLength = Lengths.VILLAGE)
	public String hondurasVillage;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Honduras municipality name", maxLength = Lengths.DEFAULT)
	public String hondurasMunicipality;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Honduras department name", maxLength = Lengths.DEFAULT)
	public String hondurasDepartment;

    @Schema(description = "country")
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
