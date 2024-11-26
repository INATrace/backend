package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2023_04_17_11_00__Update_Product_Type_FieldName implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        List<ProductType> productTypeList = Queries.getAll(em, ProductType.class);

        for (ProductType productType : productTypeList) {
           productType.setCode(productType.getName().trim().replace(" ", "_").toUpperCase());
        }
    }
}
