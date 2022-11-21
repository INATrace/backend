package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoQualitySegmentType;
import io.swagger.annotations.ApiModelProperty;

public class ApiBeycoCoffeeQuality {

    @ApiModelProperty(value = "Quality of coffee beans")
    private BeycoQualitySegmentType type;

    public BeycoQualitySegmentType getType() {
        return type;
    }

    public void setType(BeycoQualitySegmentType type) {
        this.type = type;
    }
}
