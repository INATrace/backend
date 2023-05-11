package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProductType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class FarmPlantInformation extends BaseEntity {

	@ManyToOne
	private ProductType productType;

	@ManyToOne
	private UserCustomer userCustomer;

	@Column(length = Lengths.DEFAULT)
	private BigDecimal plantCultivatedArea;

	@Column(length = Lengths.DEFAULT)
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
