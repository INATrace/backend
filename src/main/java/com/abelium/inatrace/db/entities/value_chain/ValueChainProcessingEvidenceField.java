package com.abelium.inatrace.db.entities.value_chain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;

/**
 * Intermediate entity between value chain and processing evidence field.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Entity
@Table
public class ValueChainProcessingEvidenceField extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private ProcessingEvidenceField processingEvidenceField;

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public ProcessingEvidenceField getProcessingEvidenceField() {
		return processingEvidenceField;
	}

	public void setProcessingEvidenceField(ProcessingEvidenceField processingEvidenceField) {
		this.processingEvidenceField = processingEvidenceField;
	}

	public ValueChainProcessingEvidenceField(ValueChain valueChain, ProcessingEvidenceField processingEvidenceField) {
		super();
		this.valueChain = valueChain;
		this.processingEvidenceField = processingEvidenceField;
	}

	public ValueChainProcessingEvidenceField() {
		super();
	}
	
}
