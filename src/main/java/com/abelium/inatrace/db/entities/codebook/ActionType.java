package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Codebook entity for action types.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class ActionType extends CodebookBaseEntity {

	@ManyToOne
	private FacilityType facilityType;

	public ActionType() {
		super();
	}

	public ActionType(String code, String label) {
		super(code, label);
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}
}
