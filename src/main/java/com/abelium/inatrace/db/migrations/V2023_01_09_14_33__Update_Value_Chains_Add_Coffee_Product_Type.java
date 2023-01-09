package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.product.ProductType;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;

public class V2023_01_09_14_33__Update_Value_Chains_Add_Coffee_Product_Type implements JpaMigration {

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
    }
}
