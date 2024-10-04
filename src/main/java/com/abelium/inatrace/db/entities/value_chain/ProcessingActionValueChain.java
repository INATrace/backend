package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Intermediate entity between value chain and company.
 *
 * @author Borche Paspalovski, Sunesis d.o.o.
 */
@Entity
public class ProcessingActionValueChain extends BaseEntity {

	@ManyToOne(optional = false)
	private ProcessingAction processingAction;

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

}
