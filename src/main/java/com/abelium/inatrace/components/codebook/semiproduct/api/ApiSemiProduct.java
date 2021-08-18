package com.abelium.inatrace.components.codebook.semiproduct.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import org.springframework.validation.annotation.Validated;

/**
 * SemiProduct API model object.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiSemiProduct extends ApiBaseEntity {

	private String name;

	private String description;

	private ApiMeasureUnitType apiMeasureUnitType;

	private Boolean isSKU;

	private Boolean isBuyable;

	private Boolean isSKUEndCustomer;

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

	public ApiMeasureUnitType getApiMeasureUnitType() {
		return apiMeasureUnitType;
	}

	public void setApiMeasureUnitType(ApiMeasureUnitType apiMeasureUnitType) {
		this.apiMeasureUnitType = apiMeasureUnitType;
	}

	public Boolean getSKU() {
		return isSKU;
	}

	public void setSKU(Boolean SKU) {
		isSKU = SKU;
	}

	public Boolean getBuyable() {
		return isBuyable;
	}

	public void setBuyable(Boolean buyable) {
		isBuyable = buyable;
	}

	public Boolean getSKUEndCustomer() {
		return isSKUEndCustomer;
	}

	public void setSKUEndCustomer(Boolean SKUEndCustomer) {
		isSKUEndCustomer = SKUEndCustomer;
	}

}
