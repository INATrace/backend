package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class FarmPlantInformation extends BaseEntity {

	@ManyToOne
	private ProductType productType;

	@ManyToOne
	private UserCustomer userCustomer;

	@Column
	private BigDecimal plantCultivatedArea;

	@Column
	private Integer numberOfPlants;

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
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
