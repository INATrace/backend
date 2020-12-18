package com.abelium.INATrace.components.product.api;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductOrigin {

	@Length(max = 2000)
	@ApiModelProperty(value = "origin - text and quantity input - Briefly describe where the product or its ingredients are produced", position = 1)
	public String text;

	@ApiModelProperty(value = "origin - farmer location", position = 2)
	@Valid
    public List<ApiLocation> locations;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ApiLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<ApiLocation> locations) {
		this.locations = locations;
	}
}
