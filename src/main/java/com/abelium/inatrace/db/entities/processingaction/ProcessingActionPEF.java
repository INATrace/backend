package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

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

	public ProcessingEvidenceField getProcessingEvidenceField() {
		return processingEvidenceField;
	}

	public void setProcessingEvidenceField(ProcessingEvidenceField processingEvidenceField) {
		this.processingEvidenceField = processingEvidenceField;
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
		@NotNull ProcessingEvidenceField processingEvidenceField,
		Boolean mandatory,
		Boolean requiredOnQuote) {
		super();
		this.processingAction = processingAction;
		this.processingEvidenceField = processingEvidenceField;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
	}

	public ProcessingActionPEF() {
		super();
	}
	
}
