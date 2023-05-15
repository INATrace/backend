package com.abelium.inatrace.components.processingaction.api;

import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Processing action output semi-product API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiProcessingActionOutputSemiProduct extends ApiSemiProduct {

	@ApiModelProperty(value = "Repacked outputs of this output semi-product")
	private Boolean repackedOutput;

	@ApiModelProperty(value = "Maximum output weight when repacked outputs")
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
