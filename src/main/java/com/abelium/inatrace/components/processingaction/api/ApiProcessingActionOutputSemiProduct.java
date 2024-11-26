package com.abelium.inatrace.components.processingaction.api;

import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Processing action output semi-product API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiProcessingActionOutputSemiProduct extends ApiSemiProduct {

	@Schema(description = "Repacked outputs of this output semi-product")
	private Boolean repackedOutput;

	@Schema(description = "Maximum output weight when repacked outputs")
	private BigDecimal maxOutputWeight;

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
