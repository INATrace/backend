package com.abelium.inatrace.db.entities.analytics;

import com.abelium.inatrace.api.types.Lengths;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RequestGeoLocation {

    /**
     * City  
     */
    @Column(length = Lengths.CITY)
    private String city;
    
    /**
     * Country
     */
    @Column(length = Lengths.COUNTRY_NAME)
    private String country;
    
    /**
     * Latitude
     */
    @Column
    private Double latitude;

    /**
     * Longitude
     */
    @Column
    private Double longitude;

    public RequestGeoLocation() {
    }

	public RequestGeoLocation(String city, String country, Double latitude, Double longitude) {
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
    
}
