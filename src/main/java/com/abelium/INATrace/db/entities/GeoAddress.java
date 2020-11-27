package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GeoAddress extends Address {
	
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
