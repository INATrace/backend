package com.abelium.inatrace.components.product.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelBatchCheckOrigin extends ApiBaseEntity {

	@NotNull
	@ApiModelProperty(value = "Batch number", position = 1)
	@Pattern(regexp = "^[\\p{Alnum}]*$")
	public String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
