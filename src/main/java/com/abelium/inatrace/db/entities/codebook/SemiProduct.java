package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TimestampEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Codebook entity for semi products.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class SemiProduct extends TimestampEntity {

	/**
	 * Name of the semi product
	 */
	@Column
	private String name;

	/**
	 * Description of the semi product
	 */
	@Column
	private String description;

	/**
	 * Type of measurement unit for quantity of the product (used for stock orders of this product)
	 */
	@ManyToOne
	private MeasureUnitType measurementUnitType;

	/**
	 * Translations for semi product
	 */
	@OneToMany(mappedBy = "semiProduct", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SemiProductTranslation> semiProductTranslations = new ArrayList<>();

	/**
	 * Whether the product is considered as Stock keeping unit (at producer)
	 */
	private Boolean isSKU;

	/**
	 * Whether the product is buyable
	 */
	private Boolean isBuyable;

	/**
	 * Whether the product is considered as Stock keeping unit (for end customer)
	 */
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

	public MeasureUnitType getMeasurementUnitType() {
		return measurementUnitType;
	}

	public void setMeasurementUnitType(MeasureUnitType measurementUnitType) {
		this.measurementUnitType = measurementUnitType;
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

	public List<SemiProductTranslation> getSemiProductTranslations() {
		return semiProductTranslations;
	}

	public void setSemiProductTranslations(List<SemiProductTranslation> semiProductTranslations) {
		this.semiProductTranslations = semiProductTranslations;
	}
}
