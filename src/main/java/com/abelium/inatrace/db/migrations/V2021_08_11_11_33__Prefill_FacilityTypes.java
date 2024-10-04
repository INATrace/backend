package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class V2021_08_11_11_33__Prefill_FacilityTypes implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<FacilityType> facilityTypes = List.of(
				new FacilityType("WASHING_STATION", "Washing station"),
				new FacilityType("DRYING_BED", "Drying bed"),
				new FacilityType("HULLING_STATION", "Hulling station"),
				new FacilityType("STORAGE", "Storage"));

		facilityTypes.forEach(em::persist);
	}
}
