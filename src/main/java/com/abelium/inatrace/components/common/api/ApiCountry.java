package com.abelium.inatrace.components.common.api;

import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class ApiCountry {

    @NotNull
    @Schema(description = "Db id.", requiredMode = Schema.RequiredMode.REQUIRED)
    public Long id;

    @NotNull
    @Size(max = Lengths.COUNTRY_CODE)
    @Schema(description = "Two letter country code of country (ISO 3166-1 alpha-2 code).", maxLength = Lengths.COUNTRY_CODE, requiredMode = Schema.RequiredMode.REQUIRED)
    public String code = null;

    @NotNull
    @Size(max = Lengths.COUNTRY_NAME)
    @Schema(description = "Country name.", maxLength = Lengths.COUNTRY_NAME, requiredMode = Schema.RequiredMode.REQUIRED)
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
