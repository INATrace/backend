package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoQualitySegmentType;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiBeycoCoffeeQuality {

    @Schema(description = "Quality of coffee beans")
    private BeycoQualitySegmentType type;

    public BeycoQualitySegmentType getType() {
        return type;
    }

    public void setType(BeycoQualitySegmentType type) {
        this.type = type;
    }
}
