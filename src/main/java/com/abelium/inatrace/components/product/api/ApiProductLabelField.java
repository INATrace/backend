package com.abelium.inatrace.components.product.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelField {

	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Field name in Product", position = 1)
	public String name;

	@ApiModelProperty(value = "Visible on FE", position = 2)
	public Boolean visible = true;
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Section on FE", position = 3)
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
