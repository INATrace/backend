package com.abelium.inatrace.components.usercustomer.api;

import com.abelium.inatrace.components.product.api.ApiLocation;
import io.swagger.annotations.ApiModelProperty;

public class ApiUserCustomerLocation extends ApiLocation {

    @ApiModelProperty(value = "Is location publicly visible on a map")
    private Boolean isPubliclyVisible;

    public Boolean getPubliclyVisible() {
        return isPubliclyVisible;
    }

    public void setPubliclyVisible(Boolean publiclyVisible) {
        isPubliclyVisible = publiclyVisible;
    }
}
