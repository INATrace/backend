package com.abelium.inatrace.components.codebook.facility_type;

import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.db.entities.codebook.FacilityType;

/**
 * Mapper for FacilityType entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class FacilityTypeMapper {

	private FacilityTypeMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiFacilityType toApiFacilityType(FacilityType entity) {

		ApiFacilityType apiFacilityType = new ApiFacilityType();
		apiFacilityType.setId(entity.getId());
		apiFacilityType.setCode(entity.getCode());
		apiFacilityType.setLabel(entity.getLabel());

		return apiFacilityType;
	}
}
