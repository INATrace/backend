package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.codebook.ProductTypeTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2023_04_12_14_00__Update_Product_Type_Translations implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<ProductType> productTypeList = Queries.getAll(em, ProductType.class);

        for (ProductType productType : productTypeList) {
            for (Language language : List.of(Language.EN, Language.DE, Language.RW, Language.ES)) {
                ProductTypeTranslation productTypeTranslation = new ProductTypeTranslation();
                productTypeTranslation.setName(productType.getName());
                productTypeTranslation.setDescription(productType.getDescription());
                productTypeTranslation.setLanguage(language);
                productTypeTranslation.setProductType(productType);
                em.persist(productTypeTranslation);
            }
        }
    }
}
