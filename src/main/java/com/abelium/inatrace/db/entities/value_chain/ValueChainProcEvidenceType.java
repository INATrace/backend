package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Intermediate entity between value chain and proc. evidence type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = { @Index(columnList = "valueChain_id, processingEvidenceType_id", unique = true) })
public class ValueChainProcEvidenceType extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private ProcessingEvidenceType processingEvidenceType;

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
