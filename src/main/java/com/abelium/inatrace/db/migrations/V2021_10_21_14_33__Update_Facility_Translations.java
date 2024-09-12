package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilityTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * @author Nejc Rebernik, Sunesis d.o.o.
 */
public class V2021_10_21_14_33__Update_Facility_Translations implements JpaMigration {
    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<Facility> facilityList = Queries.getAll(em, Facility.class);

        for (Facility facility : facilityList) {
            for (Language language : List.of(Language.EN, Language.DE, Language.RW, Language.ES)) {
                FacilityTranslation facilityTranslation = new FacilityTranslation();
                facilityTranslation.setFacility(facility);
                facilityTranslation.setLanguage(language);
                facilityTranslation.setName(facility.getName());
                em.persist(facilityTranslation);
            }
        }
    }
}
