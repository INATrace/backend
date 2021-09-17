package com.abelium.inatrace.db.entities.processingaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;

@Entity
public class ProcessingActionPEF extends BaseEntity {

	@ManyToOne
	@NotNull
	private ProcessingAction processingAction;

	@ManyToOne
	@NotNull
	private ProcessingEvidenceField processingEvidenceField;

	@Column
	private Boolean mandatory;
	
	@Column
	private Boolean requiredOnQuote;

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public ProcessingEvidenceField getProcessingEvidenceType() {
		return processingEvidenceField;
	}

	public void setProcessingEvidenceType(ProcessingEvidenceField processingEvidenceType) {
		this.processingEvidenceField = processingEvidenceType;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getRequiredOnQuote() {
		return requiredOnQuote;
	}

	public void setRequiredOnQuote(Boolean requiredOnQuote) {
		this.requiredOnQuote = requiredOnQuote;
	}

	public ProcessingActionPEF(
		@NotNull ProcessingAction processingAction,
		@NotNull ProcessingEvidenceField processingEvidenceType,
		Boolean mandatory,
		Boolean requiredOnQuote) {
		super();
		this.processingAction = processingAction;
		this.processingEvidenceField = processingEvidenceType;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
	}

	public ProcessingActionPEF() {
		super();
	}
	
}
