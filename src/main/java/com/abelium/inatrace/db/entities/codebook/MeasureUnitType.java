package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * Codebook entity for measuring unit types.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class MeasureUnitType extends CodebookBaseEntity {

	private BigDecimal weight;

	@ManyToOne
	private MeasureUnitType underlyingMeasurementUnitType;

	public MeasureUnitType() {
		super();
	}

	public MeasureUnitType(String code, String label, BigDecimal weight) {
		super(code, label);
		this.weight = weight;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public MeasureUnitType getUnderlyingMeasurementUnitType() {
		return underlyingMeasurementUnitType;
	}

	public void setUnderlyingMeasurementUnitType(MeasureUnitType underlyingMeasurementUnitType) {
		this.underlyingMeasurementUnitType = underlyingMeasurementUnitType;
	}
}
