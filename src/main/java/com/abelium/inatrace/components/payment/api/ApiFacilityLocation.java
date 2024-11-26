package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.components.product.api.ApiLocation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiFacilityLocation extends ApiLocation {

    @Schema(description = "Is location publicly visible on a map")
    private Boolean isPubliclyVisible;

    public Boolean getPubliclyVisible() {
        return isPubliclyVisible;
    }

    public void setPubliclyVisible(Boolean publiclyVisible) {
        isPubliclyVisible = publiclyVisible;
    }
}
