package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;

@Validated
public class ApiProductOrigin {

	@Size(max = 2000)
	@Schema(description = "origin - text and quantity input - Briefly describe where the product or its ingredients are produced", maxLength = 2000)
	public String text;

	@Schema(description = "origin - farmer location")
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
