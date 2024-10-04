package com.abelium.inatrace.components.codebook.processingevidencefield.api;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiProcessingEvidenceFieldTranslation extends BaseEntity {

    @Schema(description = "Processing evidence field label")
    private String label;

    @Schema(description = "Processing evidence field language")
    private Language language;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
