package com.abelium.inatrace.components.beycoorder.api;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class ApiBeycoPortOfExport {

    @ApiModelProperty(value = "Address of facility")
    @Min(1)
    private String address;

    @ApiModelProperty(value = "Latitude of facility")
    private Double latitude;

    @ApiModelProperty(value = "Longitude of facility")
    private Double longitude;

    @ApiModelProperty(value = "Country of facility")
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
