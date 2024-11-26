package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public class ApiFarmPlantInformation {

	@Schema(description = "Product type")
	public ApiProductType productType;

	@Schema(description = "Area cultivated with plant (ha)")
	public BigDecimal plantCultivatedArea;

	@Schema(description = "Number of plants")
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
