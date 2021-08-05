package com.abelium.inatrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.abelium.inatrace.api.types.Lengths;

@Embeddable
@MappedSuperclass
public class Address {
	
	/**
	 * address
	 */
	@Column(length = Lengths.ADDRESS)
	private String address;
	
	/**
	 * city
	 */
	@Column(length = Lengths.CITY)
	private String city;

	/**
	 * state / province / region
	 */
	@Column(length = Lengths.STATE)
	private String state;

	/**
	 * ZIP / postal code / p.p. box
	 */
	@Column(length = Lengths.ZIPCODE)
	private String zip;
	
	/**
	 * country
	 */
	@ManyToOne
	private Country country;

	
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
