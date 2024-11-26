package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApiProductBase extends ApiBaseEntity {

	@NotNull
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "product name", maxLength = Lengths.DEFAULT)
	public String name;

	@Size(max = 2000)
	@Schema(description = "product description", maxLength = 2000)
	public String description;

	@Schema(description = "origin - text and location ")
	@Valid
	public ApiProductOrigin origin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApiProductOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(ApiProductOrigin origin) {
		this.origin = origin;
	}

}
