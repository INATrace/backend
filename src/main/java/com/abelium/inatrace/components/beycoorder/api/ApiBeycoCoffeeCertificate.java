package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoCertificateType;
import io.swagger.annotations.ApiModelProperty;

public class ApiBeycoCoffeeCertificate {

    @ApiModelProperty(value = "Certificate of coffee beans")
    private BeycoCertificateType type;

    public BeycoCertificateType getType() {
        return type;
    }

    public void setType(BeycoCertificateType type) {
        this.type = type;
    }
}
