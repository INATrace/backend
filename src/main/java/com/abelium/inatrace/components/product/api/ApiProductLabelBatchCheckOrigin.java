package com.abelium.inatrace.components.product.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabelBatchCheckOrigin extends ApiBaseEntity {

	@NotNull
	@Schema(description = "Batch number")
	@Pattern(regexp = "^\\p{Alnum}*$")
	public String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
