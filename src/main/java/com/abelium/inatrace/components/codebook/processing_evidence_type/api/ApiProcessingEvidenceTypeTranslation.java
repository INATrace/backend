package com.abelium.inatrace.components.codebook.processing_evidence_type.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiProcessingEvidenceTypeTranslation extends ApiBaseEntity {

    @Schema(description = "Processing evidence type label")
    private String label;

    @Schema(description = "Processing evidence type language")
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
