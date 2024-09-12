package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class V2021_08_11_11_41__Prefill_MeasureUnitTypes implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<MeasureUnitType> measureUnitTypes = List.of(
				new MeasureUnitType("VOLUME_L", "liter", null),
				new MeasureUnitType("WEIGHT_KG", "kg", new BigDecimal(1)),
				new MeasureUnitType("BAG_60", "Bag (60 kg)", new BigDecimal(60)));

		measureUnitTypes.forEach(em::persist);
	}
}
