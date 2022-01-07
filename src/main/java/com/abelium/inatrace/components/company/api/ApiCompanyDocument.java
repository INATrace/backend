package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.types.CompanyDocumentCategory;
import com.abelium.inatrace.types.CompanyDocumentType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public class ApiCompanyDocument {
	
	@ApiModelProperty(value = "type of the document (link, file)", position = 1)
	public CompanyDocumentType type;
	
	@ApiModelProperty(value = "document category", position = 2)
	public CompanyDocumentCategory category;

	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "name", position = 3)
	public String name;
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "description of this document", position = 4)
	public String description;

	@Length(max = 2000)
	@ApiModelProperty(value = "quote of this document", position = 5)
	public String quote;
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "link", position = 6)
	public String link;
	
	@ApiModelProperty(value = "document", position = 7)
	@Valid
	public ApiDocument document;


	public CompanyDocumentType getType() {
		return type;
	}

	public void setType(CompanyDocumentType type) {
		this.type = type;
	}

	public CompanyDocumentCategory getCategory() {
		return category;
	}

	public void setCategory(CompanyDocumentCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ApiDocument getDocument() {
		return document;
	}

	public void setDocument(ApiDocument document) {
		this.document = document;
	}
}
