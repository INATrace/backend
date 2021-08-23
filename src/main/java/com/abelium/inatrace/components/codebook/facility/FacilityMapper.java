package com.abelium.inatrace.components.codebook.facility;

import com.abelium.inatrace.components.codebook.facility.api.ApiFacility;
import com.abelium.inatrace.db.entities.facility.Facility;

/**
 * Mapper for Facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class FacilityMapper {

  private FacilityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static ApiFacility toApiFacility(Facility entity) {

    ApiFacility apiFacility = new ApiFacility();
    apiFacility.setId(entity.getId());
    apiFacility.setName(entity.getName());
    apiFacility.setIsCollectionFacility(entity.getIsCollectionFacility());
    apiFacility.setIsPublic(entity.getIsPublic());
    // apiFacility.setLocation(entity.getLocation());
    // TODO: How are you mapping collections? Is there a utility?

    return apiFacility;
  }
}
