package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Codebook entity for measuring unit types.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class MeasureUnitType extends CodebookBaseEntity {

	private Integer weight;

	@ManyToOne
	private MeasureUnitType underlyingMeasurementUnitType;

	public MeasureUnitType() {
		super();
	}

	public MeasureUnitType(String code, String label, Integer weight) {
		super(code, label);
		this.weight = weight;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public MeasureUnitType getUnderlyingMeasurementUnitType() {
		return underlyingMeasurementUnitType;
	}

	public void setUnderlyingMeasurementUnitType(MeasureUnitType underlyingMeasurementUnitType) {
		this.underlyingMeasurementUnitType = underlyingMeasurementUnitType;
	}
}
