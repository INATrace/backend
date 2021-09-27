package com.abelium.inatrace.components.processingactiontranslation.api;


import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiModelProperty;

public class ApiProcessingActionTranslation {

    @ApiModelProperty(value = "Processing action name")
    private String name;

    @ApiModelProperty(value = "Processing action description")
    private String description;

    @ApiModelProperty(value = "Processing action language")
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

    public ApiProcessingActionTranslation() {
        super();
    }

    public ApiProcessingActionTranslation(String name, String description, Language language) {
        super();
        this.name = name;
        this.description = description;
        this.language = language;
    }

}
