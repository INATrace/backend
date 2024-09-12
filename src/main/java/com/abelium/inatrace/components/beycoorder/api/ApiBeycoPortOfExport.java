package com.abelium.inatrace.components.beycoorder.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;

public class ApiBeycoPortOfExport {

    @Schema(description = "Address of facility")
    @Min(1)
    private String address;

    @Schema(description = "Latitude of facility")
    private Double latitude;

    @Schema(description = "Longitude of facility")
    private Double longitude;

    @Schema(description = "Country of facility")
    @Min(1)
    private String country;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
