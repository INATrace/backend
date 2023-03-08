package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.common.UserCustomerProductType;
import com.abelium.inatrace.db.entities.product.ProductType;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

public class V2023_02_20_13_40__Update_User_Customer_Product_Type implements JpaMigration {

	@Override
	public void migrate(EntityManager em, Environment environment) throws Exception {

		List<ProductType> productTypeList = Queries.getAll(em, ProductType.class);

		List<UserCustomer> userCustomerList = Queries.getAll(em, UserCustomer.class);

		userCustomerList.forEach(userCustomer -> {
			UserCustomerProductType userCustomerProductType = new UserCustomerProductType();
			userCustomerProductType.setUserCustomer(userCustomer);
			userCustomerProductType.setProductType(productTypeList.get(0));

			em.persist(userCustomerProductType);
		});
	}
}
