package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Intermediate entity between value chain and measure unit type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = {@Index(columnList = "valueChain_id, measureUnitType_id", unique = true) })
public class ValueChainMeasureUnitType extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private MeasureUnitType measureUnitType;

	public ValueChainMeasureUnitType() {
		super();
	}

	public ValueChainMeasureUnitType(ValueChain valueChain, MeasureUnitType measureUnitType) {
		super();
		this.valueChain = valueChain;
		this.measureUnitType = measureUnitType;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public MeasureUnitType getMeasureUnitType() {
		return measureUnitType;
	}

	public void setMeasureUnitType(MeasureUnitType measureUnitType) {
		this.measureUnitType = measureUnitType;
	}

}
