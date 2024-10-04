package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.product.api.ApiLocation;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiUserCustomerLocation extends ApiLocation {

    @Schema(description = "Is location publicly visible on a map")
    private Boolean isPubliclyVisible;

    public Boolean getPubliclyVisible() {
        return isPubliclyVisible;
    }

    public void setPubliclyVisible(Boolean publiclyVisible) {
        isPubliclyVisible = publiclyVisible;
    }
}
