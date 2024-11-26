package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

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

	/**
	 * Specifies if this output should be repacked (generated multiple output stock order).
	 */
	@Column
	private Boolean repackedOutput;

	/**
	 * Specified the maximum output weight when using 'repackedOutputs' (set to value 'true').
	 */
	@Column
	private BigDecimal maxOutputWeight;

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

	public Boolean getRepackedOutput() {
		return repackedOutput;
	}

	public void setRepackedOutput(Boolean repackedOutput) {
		this.repackedOutput = repackedOutput;
	}

	public BigDecimal getMaxOutputWeight() {
		return maxOutputWeight;
	}

	public void setMaxOutputWeight(BigDecimal maxOutputWeight) {
		this.maxOutputWeight = maxOutputWeight;
	}

}
