package com.abelium.inatrace.components.product.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@Validated
public class ApiProductLabelFieldValue {

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Field name in Product", position = 1)
	public String name;

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "Section on FE", position = 2)
	public String section;
	
	@ApiModelProperty(value = "Field value in Product", position = 3)
	public Object value;
	
	public ApiProductLabelFieldValue() {
	}

	public ApiProductLabelFieldValue(String name, String section, Object value) {
		this.name = name;
		this.section = section;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
