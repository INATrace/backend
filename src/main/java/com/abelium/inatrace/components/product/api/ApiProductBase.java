package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class ApiProductBase extends ApiBaseEntity {

	@NotNull
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "product name", position = 2)
	public String name;

	@Length(max = 2000)
	@ApiModelProperty(value = "product description", position = 4)
	public String description;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "nutritional Value - list the nutritional value of the product", position = 6)
	public String nutritionalValue;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "how to Use / Recipes - Describe the best way to use the product (e.g. recipes, how to apply the product...)", position = 7)
	public String howToUse;

	@ApiModelProperty(value = "origin - text and location ", position = 8)
	@Valid
	public ApiProductOrigin origin;

	@ApiModelProperty(value = "Key Markets, market name - share number map", position = 10)
    public Map<String, Double> keyMarketsShare;

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

	public String getNutritionalValue() {
		return nutritionalValue;
	}

	public void setNutritionalValue(String nutritionalValue) {
		this.nutritionalValue = nutritionalValue;
	}

	public String getHowToUse() {
		return howToUse;
	}

	public void setHowToUse(String howToUse) {
		this.howToUse = howToUse;
	}

	public ApiProductOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(ApiProductOrigin origin) {
		this.origin = origin;
	}

	public Map<String, Double> getKeyMarketsShare() {
		return keyMarketsShare;
	}

	public void setKeyMarketsShare(Map<String, Double> keyMarketsShare) {
		this.keyMarketsShare = keyMarketsShare;
	}
}
