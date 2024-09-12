package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoCertificateType;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiBeycoCoffeeCertificate {

    @Schema(description = "Certificate of coffee beans")
    private BeycoCertificateType type;

    public BeycoCertificateType getType() {
        return type;
    }

    public void setType(BeycoCertificateType type) {
        this.type = type;
    }
}
