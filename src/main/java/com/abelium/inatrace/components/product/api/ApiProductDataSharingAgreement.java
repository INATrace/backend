package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.common.api.ApiDocument;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiProductDataSharingAgreement {

	@ApiModelProperty(value = "Description of this document")
	private String description;

	@ApiModelProperty(value = "Document attached to the product")
	private ApiDocument document;

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
