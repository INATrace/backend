package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;
import jakarta.persistence.Entity;

/**
 * Codebook entity for facility types.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class FacilityType extends CodebookBaseEntity {
  
	public FacilityType() {
		super();
	}

	public FacilityType(String code, String label) {
		super(code, label);
	}
}
