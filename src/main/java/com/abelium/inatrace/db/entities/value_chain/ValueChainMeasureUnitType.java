package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Intermediate entity between value chain and measure unit type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = { @Index(columnList = "valueChain_id, measureUnitType_id", unique = true) })
public class ValueChainMeasureUnitType extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private MeasureUnitType measureUnitType;

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
