package com.abelium.inatrace.components.facility.api;

import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiModelProperty;

public class ApiFacilityTranslation {

    @ApiModelProperty(value = "Facility name")
    private String name;

    @ApiModelProperty(value = "Facility language")
    private Language language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
