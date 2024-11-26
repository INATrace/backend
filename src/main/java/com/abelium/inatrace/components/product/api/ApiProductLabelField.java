package com.abelium.inatrace.components.product.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

@Validated
public class ApiProductLabelField {

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Field name in Product")
	public String name;

	@Schema(description = "Visible on FE")
	public Boolean visible = true;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Section on FE")
	public String section;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
