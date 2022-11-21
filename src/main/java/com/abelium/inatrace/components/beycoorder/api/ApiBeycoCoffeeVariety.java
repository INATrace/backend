package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoVarietyType;
import io.swagger.annotations.ApiModelProperty;

public class ApiBeycoCoffeeVariety {

    @ApiModelProperty(value = "Variety of coffee beans")
    private BeycoVarietyType type;

    @ApiModelProperty(value = "Optional custom variety of coffee beans")
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
