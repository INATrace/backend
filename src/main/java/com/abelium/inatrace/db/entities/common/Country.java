package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

// Do not make a db relation to this table, use the country code to refer to it 
@Entity
@NamedQueries({
		@NamedQuery(name = "Country.getCountryByCode",
		            query = "SELECT c FROM Country c WHERE c.code = :code")
})
public class Country extends BaseEntity {
    
    /**
     * Two-letter country code
     */
    @Column(unique = true, nullable = false, length = Lengths.COUNTRY_CODE)
    private String code;

    /**
     * Country name
     */
    @Column(nullable = false, length = Lengths.COUNTRY_NAME)
    private String name;

	@Column
	private Double latitude;

	@Column
	private Double longitude;
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
