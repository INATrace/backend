package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ApiProductBase extends ApiBaseEntity {

	@NotNull
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "product name", position = 2)
	public String name;

	@Length(max = 2000)
	@ApiModelProperty(value = "product description", position = 4)
	public String description;

	@ApiModelProperty(value = "origin - text and location ", position = 8)
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
