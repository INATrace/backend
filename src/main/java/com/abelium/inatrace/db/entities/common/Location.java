package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public class Location extends BaseEntity {
	
	@Version
	private long entityVersion;
	
	/**
	 * location address
	 */
	@Embedded
	private Address address;
	
	/**
	 * location latitude 
	 */
	@Column
	private Double latitude;
	
	/**
	 * location longitude
	 */
	@Column
	private Double longitude;
	
	/**
	 * Number of farmers at this location
	 */
	@Column
	private Integer numberOfFarmers;
	
	/**
	 * Pin name 
	 */
	@Column(length = Lengths.DEFAULT)
	private String pinName;
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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
