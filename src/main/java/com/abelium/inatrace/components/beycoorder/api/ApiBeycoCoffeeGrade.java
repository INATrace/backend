package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoGradeType;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiBeycoCoffeeGrade {

    @Schema(description = "Grade of coffee beans")
    private BeycoGradeType type;

    public BeycoGradeType getType() {
        return type;
    }

    public void setType(BeycoGradeType type) {
        this.type = type;
    }
}
