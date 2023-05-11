package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.common.UserCustomerPlantInformation;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

public class V2023_05_11_10_30__Update_User_Customer_Plant_Information_Relation implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<UserCustomerPlantInformation> userCustomerPlantInformationList = Queries.getAll(em, UserCustomerPlantInformation.class);

		if (userCustomerPlantInformationList != null && !userCustomerPlantInformationList.isEmpty()) {

			userCustomerPlantInformationList.forEach(
					ucpi -> {
						ucpi.getUserCustomer().getFarmPlantInformationList().add(ucpi.getPlantInformation());
						ucpi.getPlantInformation().setUserCustomer(ucpi.getUserCustomer());
					});
		}
	}
}
