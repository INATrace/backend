package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import jakarta.persistence.*;

/**
 * Intermediate entity between value chain and facility type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = { @Index(columnList = "valueChain_id, facilityType_id", unique = true) })
public class ValueChainFacilityType extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private FacilityType facilityType;

	public ValueChainFacilityType() {
		super();
	}

	public ValueChainFacilityType(ValueChain valueChain, FacilityType facilityType) {
		super();
		this.valueChain = valueChain;
		this.facilityType = facilityType;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

}
