package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.facility.Facility;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Intermediate entity between value chain and company.
 *
 * @author Borche Paspalovski, Sunesis d.o.o.
 */
@Entity
public class FacilityValueChain extends BaseEntity {

	@ManyToOne(optional = false)
	private Facility facility;

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

}
