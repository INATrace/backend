package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class ProcessingActionProcessingEvidenceType extends TimestampEntity {

	@Version
	private long entityVersion;

	@ManyToOne
	@NotNull
	private ProcessingAction processingAction;

	@ManyToOne
	@NotNull
	private ProcessingEvidenceType processingEvidenceType;

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public ProcessingEvidenceType getProcessingEvidenceType() {
		return processingEvidenceType;
	}

	public void setProcessingEvidenceType(ProcessingEvidenceType processingEvidenceType) {
		this.processingEvidenceType = processingEvidenceType;
	}

	public ProcessingActionProcessingEvidenceType(
		@NotNull ProcessingAction processingAction,
		@NotNull ProcessingEvidenceType processingEvidenceType) {
		super();
		this.processingAction = processingAction;
		this.processingEvidenceType = processingEvidenceType;
	}

	public ProcessingActionProcessingEvidenceType() {
		super();
	}
	
}
