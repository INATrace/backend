package com.abelium.inatrace.components.codebook.semiproduct.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiModelProperty;

public class ApiSemiProductTranslation extends ApiBaseEntity {

    @ApiModelProperty(value = "Semi product name")
    private String name;

    @ApiModelProperty(value = "Semi product description")
    private String description;

    @ApiModelProperty(value = "Semi product language")
    private Language language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
