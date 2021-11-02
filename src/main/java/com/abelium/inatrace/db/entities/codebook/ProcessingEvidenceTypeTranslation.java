package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TranslatedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class ProcessingEvidenceTypeTranslation extends TranslatedEntity {

    @Version
    private Long entityVersion;

    @Column
    private String label;

    @ManyToOne
    @NotNull
    private ProcessingEvidenceType processingEvidenceType;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ProcessingEvidenceType getProcessingEvidenceType() {
        return processingEvidenceType;
    }

    public void setProcessingEvidenceType(ProcessingEvidenceType processingEvidenceType) {
        this.processingEvidenceType = processingEvidenceType;
    }
}
