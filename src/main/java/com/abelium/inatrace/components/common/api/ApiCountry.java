package com.abelium.inatrace.components.common.api;

import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Validated
public class ApiCountry {

    @NotNull
    @Schema(description = "Db id.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotNull
    @Size(max = Lengths.COUNTRY_CODE)
    @Schema(description = "Two letter country code of country (ISO 3166-1 alpha-2 code).", maxLength = Lengths.COUNTRY_CODE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String code = null;

    @NotNull
    @Size(max = Lengths.COUNTRY_NAME)
    @Schema(description = "Country name.", maxLength = Lengths.COUNTRY_NAME, requiredMode = Schema.RequiredMode.REQUIRED)
    private String name = null;

    @Schema(description = "The latitude of the country")
    private Double latitude;

    @Schema(description = "The longitude of the country")
    private Double longitude;
    
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
