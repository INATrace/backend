package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.ActionType;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class V2021_08_11_11_22__Prefill_ActionTypes implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<ActionType> actionTypes = List.of(
				new ActionType("PROCESSING", "Processing"),
				new ActionType("SHIPMENT", "Shipment"));

		actionTypes.forEach(em::persist);
	}
}
