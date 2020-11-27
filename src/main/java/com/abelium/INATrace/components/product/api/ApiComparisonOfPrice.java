package com.abelium.INATrace.components.product.api;

import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiComparisonOfPrice {
	
	@ApiModelProperty(value = "prices - string-number map", position = 1)
    public Map<String, Double> prices;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "description", position = 2)
	public String description;

	
	public Map<String, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
