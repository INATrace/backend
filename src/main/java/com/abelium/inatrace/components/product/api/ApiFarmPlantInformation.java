package com.abelium.inatrace.components.product.api;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public class ApiFarmPlantInformation {

	@ApiModelProperty(value = "Product type")
	public ApiProductType productType;

	@ApiModelProperty(value = "Area cultivated with plant (ha)")
	public BigDecimal plantCultivatedArea;

	@ApiModelProperty(value = "Number of plants")
	public Integer numberOfPlants;

	public ApiProductType getProductType() {
		return productType;
	}

	public void setProductType(ApiProductType productType) {
		this.productType = productType;
	}

	public BigDecimal getPlantCultivatedArea() {
		return plantCultivatedArea;
	}

	public void setPlantCultivatedArea(BigDecimal plantCultivatedArea) {
		this.plantCultivatedArea = plantCultivatedArea;
	}

	public Integer getNumberOfPlants() {
		return numberOfPlants;
	}

	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}
}
