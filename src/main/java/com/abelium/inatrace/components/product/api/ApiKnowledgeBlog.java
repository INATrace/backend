package com.abelium.inatrace.components.product.api;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.components.common.api.ApiDocument;

import jakarta.validation.constraints.Size;

@Validated
public class ApiKnowledgeBlog extends ApiKnowledgeBlogBase {

	@Size(max = 3000)
	@Schema(description = "Abstract (summary)", maxLength = 3000)
	public String summary;

	@Size(max = 5000)
	@Schema(description = "Content", maxLength = 5000)
	public String content;

	@Schema(description = "Documents")
	public List<ApiDocument> documents;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ApiDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ApiDocument> documents) {
		this.documents = documents;
	}
}
