package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;
import com.abelium.inatrace.db.entities.facility.Facility;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Codebook entity for facility types.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class FacilityType extends CodebookBaseEntity {
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Facility facility;

	public FacilityType() {
		super();
	}

	public FacilityType(String code, String label) {
		super(code, label);
	}
}
