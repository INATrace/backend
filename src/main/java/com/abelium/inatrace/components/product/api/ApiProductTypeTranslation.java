package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiProductTypeTranslation extends ApiBaseEntity {

    @Schema(description = "Product type name")
    private String name;

    @Schema(description = "Product type description")
    private String description;

    @Schema(description = "Product type language")
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
