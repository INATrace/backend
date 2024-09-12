package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiAddress;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiStockOrderLocation extends ApiBaseEntity {

    @Schema(description = "Location address")
    public ApiAddress address;

    @Schema(description = "Location latitude")
    public Double latitude;

    @Schema(description = "Location longitude")
    public Double longitude;

    @Schema(description = "Number of frames")
    public Integer numberOfFarmers;

    @Schema(description = "Pin name")
    public String pinName;

    public ApiAddress getAddress() {
        return address;
    }

    public void setAddress(ApiAddress address) {
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

    public Integer getNumberOfFarmers() {
        return numberOfFarmers;
    }

    public void setNumberOfFarmers(Integer numberOfFarmers) {
        this.numberOfFarmers = numberOfFarmers;
    }

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }
}
