package com.abelium.INATrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelValuesExtended extends ApiProductLabelValues {
	
	@ApiModelProperty(value = "Number of batches", position = 10)
	public Integer numberOfBatches;
	
	@ApiModelProperty(value = "Number of true 'checkAuthenticity' fields over all of batches", position = 11)
	public Integer checkAuthenticityCount;

	@ApiModelProperty(value = "Number of true 'traceOrigin' fields over all of batches", position = 11)
	public Integer traceOriginCount;

	public Integer getNumberOfBatches() {
		return numberOfBatches;
	}

	public void setNumberOfBatches(Integer numberOfBatches) {
		this.numberOfBatches = numberOfBatches;
	}

	public Integer getCheckAuthenticityCount() {
		return checkAuthenticityCount;
	}

	public void setCheckAuthenticityCount(Integer checkAuthenticityCount) {
		this.checkAuthenticityCount = checkAuthenticityCount;
	}

	public Integer getTraceOriginCount() {
		return traceOriginCount;
	}

	public void setTraceOriginCount(Integer traceOriginCount) {
		this.traceOriginCount = traceOriginCount;
	}
}
