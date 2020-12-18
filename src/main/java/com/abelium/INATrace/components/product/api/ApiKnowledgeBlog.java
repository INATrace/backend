package com.abelium.INATrace.components.product.api;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.components.common.api.ApiDocument;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiKnowledgeBlog extends ApiKnowledgeBlogBase {
	
	@ApiModelProperty(value = "Abstract (summary)", position = 4)
	@Length(max = 3000)
	public String summary;
	
	@ApiModelProperty(value = "Content", position = 5)
	@Length(max = 5000)
	public String content;
	
	@ApiModelProperty(value = "Documents", position = 8)
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
