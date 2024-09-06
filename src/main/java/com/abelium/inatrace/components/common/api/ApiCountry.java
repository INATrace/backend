package com.abelium.inatrace.components.common.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCountry {

    @NotNull
    @ApiModelProperty(value = "Db id.", position = 0)    
    public Long id;
    
    @NotNull
    @ApiModelProperty(required = true, value = "Two letter country code of country (ISO 3166-1 alpha-2 code).", position = 0)
    @Size(max = Lengths.COUNTRY_CODE)
    public String code = null;

    @NotNull
    @ApiModelProperty(required = true, value = "Country name.", position = 1)
    @Size(max = Lengths.COUNTRY_NAME)
    public String name = null;
    
    public ApiCountry() {}

    public ApiCountry(Long id, String countryCode, String name) {
        this.id = id;
        this.code = countryCode;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
