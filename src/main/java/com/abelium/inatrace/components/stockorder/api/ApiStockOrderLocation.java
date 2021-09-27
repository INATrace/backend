package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiAddress;
import io.swagger.annotations.ApiModelProperty;

public class ApiStockOrderLocation extends ApiBaseEntity {

    @ApiModelProperty(value = "Location address", position = 1)
    public ApiAddress address;

    @ApiModelProperty(value = "Location latitude", position = 2)
    public Double latitude;

    @ApiModelProperty(value = "Location longitude", position = 3)
    public Double longitude;

    @ApiModelProperty(value = "Number of frames", position = 4)
    public Integer numberOfFarmers;

    @ApiModelProperty(value = "Pin name", position = 5)
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
