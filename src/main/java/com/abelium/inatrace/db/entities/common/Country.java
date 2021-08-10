package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

// Do not make a db relation to this table, use the country code to refer to it 
@Entity
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
}
