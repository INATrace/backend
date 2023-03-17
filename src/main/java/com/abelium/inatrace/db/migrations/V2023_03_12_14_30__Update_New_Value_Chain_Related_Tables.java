package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionFacility;
import com.abelium.inatrace.db.entities.value_chain.ValueChainCompany;
import com.abelium.inatrace.db.entities.value_chain.ValueChainFacility;
import com.abelium.inatrace.db.entities.value_chain.ValueChainProcessingAction;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class V2023_03_12_14_30__Update_New_Value_Chain_Related_Tables implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		// Updating processing action and facility value chains
		// Migrating data from existing (1 value chain) to the linking tables
		List<ProcessingAction> processingActionList = Queries.getAll(em, ProcessingAction.class);

		if (processingActionList != null && !processingActionList.isEmpty()) {

			// Save already added elements 'valueChain-processingAction'
			Set<String> addedPairValueChainProcessingAction = new HashSet<>();
			// Save already added elements 'valueChain-facility'
			Set<String> addedPairValueChainFacility = new HashSet<>();
			// Save already added elements 'valueChain-company'
			Set<String> addedPairValueChainCompany = new HashSet<>();

			processingActionList.forEach(processingAction -> {

				if (processingAction.getValueChain() != null && processingAction.getValueChain().getId() != null) {

					if (processingAction.getId() != null) {
						String toBeAddedVcPa =
								processingAction.getValueChain().getId() + "-" + processingAction.getId();
						if (addedPairValueChainProcessingAction.add(toBeAddedVcPa)) {
							// ValueChainProcessingAction table
							ValueChainProcessingAction valueChainProcessingAction = new ValueChainProcessingAction();
							valueChainProcessingAction.setProcessingAction(processingAction);
							valueChainProcessingAction.setValueChain(processingAction.getValueChain());
							em.persist(valueChainProcessingAction);
						}
					}

					if (processingAction.getProcessingActionFacilities() != null) {
						for (ProcessingActionFacility processingActionFacility : processingAction.getProcessingActionFacilities()) {

							if (processingActionFacility.getFacility() != null &&
									processingActionFacility.getFacility().getCompany() != null &&
									processingActionFacility.getFacility().getCompany().getId() != null) {

								Company company = processingActionFacility.getFacility().getCompany();

								String toBeAddedValueChainCompany =
										processingAction.getValueChain().getId() + "-" + company.getId();
								// Set the default value chain for the company
								if (addedPairValueChainCompany.add(toBeAddedValueChainCompany)) {
									// ValueChainCompany table
									ValueChainCompany valueChainCompany = new ValueChainCompany();
									valueChainCompany.setValueChain(processingAction.getValueChain());
									valueChainCompany.setCompany(company);
									em.persist(valueChainCompany);
								}

								// For every facility in the company, set the value chain - facility relation
								if (company.getFacilities() != null) {

									for (Facility facility : company.getFacilities()) {

										String toBeAddedVcF =
												processingAction.getValueChain().getId() + "-" + facility.getId();
										if (addedPairValueChainFacility.add(toBeAddedVcF)) {
											// ValueChainFacility table
											ValueChainFacility valueChainFacility = new ValueChainFacility();
											valueChainFacility.setValueChain(processingAction.getValueChain());
											valueChainFacility.setFacility(facility);
											em.persist(valueChainFacility);
										}
									}
								}
							}
						}
					}
				}
			});
		}
	}
}
