package com.abelium.inatrace.components.product.api;

import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.KnowledgeBlogType;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@Validated
public class ApiKnowledgeBlogBase extends ApiBaseEntity {
	
	@ApiModelProperty(value = "Type", position = 2)
	public KnowledgeBlogType type;
	
	@ApiModelProperty(value = "Name", position = 3)
	@Size(max = Lengths.DEFAULT)
	public String title;
	
	@ApiModelProperty(value = "Date", position = 6)
	public LocalDate date;

	@ApiModelProperty(value = "Youtube URL", position = 7)
	@Size(max = Lengths.DEFAULT)
	public String youtubeUrl;	
	

	public KnowledgeBlogType getType() {
		return type;
	}

	public void setType(KnowledgeBlogType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

}
