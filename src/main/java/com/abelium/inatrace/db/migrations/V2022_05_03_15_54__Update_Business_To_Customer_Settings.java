package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.product.BusinessToCustomerSettings;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.db.entities.product.ProductLabel;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2022_05_03_15_54__Update_Business_To_Customer_Settings implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<Product> products = Queries.getAll(em, Product.class);

        for (Product product : products) {
            if (product.getBusinessToCustomerSettings() == null) {
                BusinessToCustomerSettings businessToCustomerSettings = defaultB2CSettings();
                em.persist(businessToCustomerSettings);
                product.setBusinessToCustomerSettings(businessToCustomerSettings);
            }
            for (ProductLabel productLabel : product.getLabels()) {
                if (productLabel.getContent() != null && productLabel.getContent().getBusinessToCustomerSettings() == null) {
                    BusinessToCustomerSettings businessToCustomerSettings = defaultB2CSettings();
                    em.persist(businessToCustomerSettings);
                    productLabel.getContent().setBusinessToCustomerSettings(businessToCustomerSettings);
                }
            }
        }
    }

    private BusinessToCustomerSettings defaultB2CSettings() {
        BusinessToCustomerSettings businessToCustomerSettings = new BusinessToCustomerSettings();

        businessToCustomerSettings.setPrimaryColor("#5c267b");
        businessToCustomerSettings.setSecondaryColor("#0fae94");
        businessToCustomerSettings.setTertiaryColor("#ac1b56");
        businessToCustomerSettings.setQuaternaryColor("#e3b22b");
        businessToCustomerSettings.setHeadingColor("#000000");
        businessToCustomerSettings.setTextColor("#000000");
        businessToCustomerSettings.setTabFairPrices(Boolean.TRUE);
        businessToCustomerSettings.setTabProducers(Boolean.TRUE);
        businessToCustomerSettings.setTabQuality(Boolean.TRUE);
        businessToCustomerSettings.setTabFeedback(Boolean.TRUE);

        return businessToCustomerSettings;
    }
}
