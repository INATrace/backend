package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TranslatedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
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
