package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TranslatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
public class ProcessingEvidenceFieldTranslation extends TranslatedEntity {

    @Version
    private Long entityVersion;

    @Column
    private String label;

    @ManyToOne
    @NotNull
    private ProcessingEvidenceField processingEvidenceField;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ProcessingEvidenceField getProcessingEvidenceField() {
        return processingEvidenceField;
    }

    public void setProcessingEvidenceField(ProcessingEvidenceField processingEvidenceField) {
        this.processingEvidenceField = processingEvidenceField;
    }
}
