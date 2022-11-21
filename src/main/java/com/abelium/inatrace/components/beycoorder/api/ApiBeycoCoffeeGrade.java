package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoGradeType;
import io.swagger.annotations.ApiModelProperty;

public class ApiBeycoCoffeeGrade {

    @ApiModelProperty(value = "Grade of coffee beans")
    private BeycoGradeType type;

    public BeycoGradeType getType() {
        return type;
    }

    public void setType(BeycoGradeType type) {
        this.type = type;
    }
}
