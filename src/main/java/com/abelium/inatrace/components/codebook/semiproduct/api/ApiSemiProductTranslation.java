package com.abelium.inatrace.components.codebook.semiproduct.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiSemiProductTranslation extends ApiBaseEntity {

    @Schema(description = "Semi product name")
    private String name;

    @Schema(description = "Semi product description")
    private String description;

    @Schema(description = "Semi product language")
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
