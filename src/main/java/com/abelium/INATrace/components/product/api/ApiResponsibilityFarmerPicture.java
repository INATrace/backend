package com.abelium.INATrace.components.product.api;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.components.common.api.ApiDocument;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiResponsibilityFarmerPicture {
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "description of this picture", position = 1)
	public String description;
	
	@ApiModelProperty(value = "picture document", position = 2)
	@Valid
	public ApiDocument document;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ApiDocument getDocument() {
		return document;
	}

	public void setDocument(ApiDocument document) {
		this.document = document;
	}
}
