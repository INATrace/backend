package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.product.BusinessToCustomerSettings;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2022_05_17_08_51__Update_Business_To_Customer_Settings_Graphics_Defaults implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<BusinessToCustomerSettings> businessToCustomerSettingsList = Queries.getAll(em, BusinessToCustomerSettings.class);

        for (BusinessToCustomerSettings businessToCustomerSettings : businessToCustomerSettingsList) {
            businessToCustomerSettings.setGraphicFairPrices(Boolean.TRUE);
            businessToCustomerSettings.setGraphicIncreaseOfIncome(Boolean.TRUE);
            businessToCustomerSettings.setGraphicQuality(Boolean.TRUE);
        }
    }
}
