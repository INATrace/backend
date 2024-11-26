package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListProductLabelBatchesRequest extends ApiPaginatedRequest {

	@Parameter(description = "Batch number (start of it)")
	public String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}	
}
