package com.abelium.inatrace.components.processingactiontranslation.api;


import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiProcessingActionTranslation {

    @Schema(description = "Processing action name")
    private String name;

    @Schema(description = "Processing action description")
    private String description;

    @Schema(description = "Processing action language")
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
