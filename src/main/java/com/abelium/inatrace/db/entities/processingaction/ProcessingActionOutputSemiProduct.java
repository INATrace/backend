package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Connecting entity between Processing action and supported output semi-product. Depending on the type of the
 * Processing action, more than one output semi-products is supported.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class ProcessingActionOutputSemiProduct extends BaseEntity {

	@ManyToOne
	@NotNull
	private ProcessingAction processingAction;

	@ManyToOne
	@NotNull
	private SemiProduct outputSemiProduct;

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public SemiProduct getOutputSemiProduct() {
		return outputSemiProduct;
	}

	public void setOutputSemiProduct(SemiProduct outputSemiProduct) {
		this.outputSemiProduct = outputSemiProduct;
	}

}
