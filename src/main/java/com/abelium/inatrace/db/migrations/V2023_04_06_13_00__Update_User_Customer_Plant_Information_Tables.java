package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.common.FarmPlantInformation;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.common.UserCustomerPlantInformation;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

public class V2023_04_06_13_00__Update_User_Customer_Plant_Information_Tables implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<UserCustomer> userCustomerList = Queries.getAll(em, UserCustomer.class);

		if (userCustomerList != null && !userCustomerList.isEmpty()) {

			userCustomerList.forEach(userCustomer -> {

				if (userCustomer.getFarm() != null &&
						(userCustomer.getFarm().getNumberOfPlants() != null
						|| userCustomer.getFarm().getPlantCultivatedArea() != null)) {

					FarmPlantInformation farmPlantInformation = new FarmPlantInformation();
					farmPlantInformation.setNumberOfPlants(userCustomer.getFarm().getNumberOfPlants());
					farmPlantInformation.setPlantCultivatedArea(userCustomer.getFarm().getPlantCultivatedArea());
					if (userCustomer.getProductTypes()!=null && userCustomer.getProductTypes().get(0)!=null) {
						farmPlantInformation.setProductType(userCustomer.getProductTypes().get(0).getProductType());
					}

					UserCustomerPlantInformation userCustomerPlantInformation = new UserCustomerPlantInformation();
					userCustomerPlantInformation.setUserCustomer(userCustomer);
					userCustomerPlantInformation.setPlantInformation(farmPlantInformation);
					em.persist(userCustomerPlantInformation);
				}
			});
		}
	}
}
