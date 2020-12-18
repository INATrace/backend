package com.abelium.INATrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiPaginatedRequest;

import io.swagger.annotations.ApiParam;

@Validated
public class ApiListProductLabelBatchesRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Batch number (start of it)")
	public String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}	
}
