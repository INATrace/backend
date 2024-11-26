package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public class ApiProductLabelFieldValue {

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Field name in Product", maxLength = Lengths.DEFAULT)
	public String name;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "Section on FE", maxLength = Lengths.DEFAULT)
	public String section;
	
	@Schema(description = "Field value in Product")
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
