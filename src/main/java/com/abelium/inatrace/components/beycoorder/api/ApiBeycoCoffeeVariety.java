package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoVarietyType;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiBeycoCoffeeVariety {

    @Schema(description = "Variety of coffee beans")
    private BeycoVarietyType type;

    @Schema(description = "Optional custom variety of coffee beans")
    private String customVariety;

    public BeycoVarietyType getType() {
        return type;
    }

    public void setType(BeycoVarietyType type) {
        this.type = type;
    }

    public String getCustomVariety() {
        return customVariety;
    }

    public void setCustomVariety(String customVariety) {
        this.customVariety = customVariety;
    }
}
