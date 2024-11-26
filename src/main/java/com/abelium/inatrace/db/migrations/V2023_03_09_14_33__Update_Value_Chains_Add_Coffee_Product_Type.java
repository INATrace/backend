package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.common.UserCustomerProductType;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2023_03_09_14_33__Update_Value_Chains_Add_Coffee_Product_Type implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        // Create coffee product type
        ProductType productTypeCoffee = new ProductType();
        productTypeCoffee.setName("Coffee");
        productTypeCoffee.setDescription("Coffee product type");

        em.persist(productTypeCoffee);

        // Set coffee product type to all value chains
        for (ValueChain valueChain : em.createQuery("SELECT vc FROM ValueChain vc", ValueChain.class).getResultList()) {
            valueChain.setProductType(productTypeCoffee);
        }

        // Create macadamia product type
        ProductType productTypeMacadamia = new ProductType();
        productTypeMacadamia.setName("Macadamia");
        productTypeMacadamia.setDescription("Macadamia product type");

        em.persist(productTypeMacadamia);

        // Set coffee product type to all farmers (userCustomers)
        List<UserCustomer> userCustomerList = Queries.getAll(em, UserCustomer.class);

        if (userCustomerList != null) {
            userCustomerList.forEach(userCustomer -> {
                UserCustomerProductType userCustomerProductType = new UserCustomerProductType();
                userCustomerProductType.setUserCustomer(userCustomer);
                userCustomerProductType.setProductType(productTypeCoffee);

                em.persist(userCustomerProductType);
            });
        }
    }
}
