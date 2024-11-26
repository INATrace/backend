package com.abelium.inatrace.components.product.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiDocument;
import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProcessDocument {
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "description of this document")
	public String description;
	
	@Schema(description = "certificate for this document")
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
