package com.abelium.inatrace.components.codebook.processingevidencefield.api;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiModelProperty;

public class ApiProcessingEvidenceFieldTranslation extends BaseEntity {

    @ApiModelProperty(value = "Processing evidence field label")
    private String label;

    @ApiModelProperty(value = "Processing evidence field language")
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
