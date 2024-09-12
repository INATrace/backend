package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Intermediate entity between value chain and proc. evidence type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = {@Index(columnList = "valueChain_id, processingEvidenceType_id", unique = true) })
public class ValueChainProcEvidenceType extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private ProcessingEvidenceType processingEvidenceType;

	public ValueChainProcEvidenceType() {
		super();
	}

	public ValueChainProcEvidenceType(ValueChain valueChain, ProcessingEvidenceType processingEvidenceType) {
		super();
		this.valueChain = valueChain;
		this.processingEvidenceType = processingEvidenceType;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public ProcessingEvidenceType getProcessingEvidenceType() {
		return processingEvidenceType;
	}

	public void setProcessingEvidenceType(ProcessingEvidenceType processingEvidenceType) {
		this.processingEvidenceType = processingEvidenceType;
	}

}
