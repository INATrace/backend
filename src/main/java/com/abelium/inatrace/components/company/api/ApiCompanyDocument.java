package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.types.CompanyDocumentCategory;
import com.abelium.inatrace.types.CompanyDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiCompanyDocument extends ApiBaseEntity {
	
	@Schema(description = "type of the document (link, file)")
	public CompanyDocumentType type;
	
	@Schema(description = "document category")
	public CompanyDocumentCategory category;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "name")
	public String name;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "description of this document")
	public String description;

	@Size(max = 2000)
	@Schema(description = "quote of this document", maxLength = 2000)
	public String quote;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "link")
	public String link;
	
	@Schema(description = "document")
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
