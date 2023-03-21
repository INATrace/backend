package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.value_chain.CompanyValueChain;
import com.abelium.inatrace.db.entities.value_chain.FacilityValueChain;
import com.abelium.inatrace.db.entities.value_chain.ProcessingActionValueChain;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

public class V2023_03_21_16_00__Update_New_Value_Chain_Related_Tables implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<Company> companyList = Queries.getAll(em, Company.class);

		if (companyList != null && !companyList.isEmpty()) {

			companyList.forEach(company -> {

				if (company.getProcessingActions() != null) {

					// find first processing action that has value chain
					ValueChain valueChain = null;

					for (ProcessingAction processingAction: company.getProcessingActions()){
						if (processingAction != null && processingAction.getValueChain() != null) {
							valueChain = processingAction.getValueChain();
							break;
						}
					}

					if (valueChain != null) {

						final ValueChain foundValueChain = valueChain;

						CompanyValueChain companyValueChain = new CompanyValueChain();
						companyValueChain.setValueChain(foundValueChain);
						companyValueChain.setCompany(company);
						em.persist(companyValueChain);

						// set value chain for every facility in the company
						company.getFacilities().forEach(facility -> {
							FacilityValueChain facilityValueChain = new FacilityValueChain();
							facilityValueChain.setValueChain(foundValueChain);
							facilityValueChain.setFacility(facility);
							em.persist(facilityValueChain);
						});

						company.getProcessingActions().forEach(processingAction -> {
							ProcessingActionValueChain processingActionValueChain = new ProcessingActionValueChain();
							processingActionValueChain.setProcessingAction(processingAction);
							processingActionValueChain.setValueChain(foundValueChain);
							em.persist(processingActionValueChain);
						});
					}
				}
			});
		}
	}
}
