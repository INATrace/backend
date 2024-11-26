package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.codebook.SemiProductTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * @author Nejc Rebernik, Sunesis d.o.o.
 */
public class V2021_10_24_17_22__Update_Semi_Product_Translations implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<SemiProduct> semiProductList = Queries.getAll(em, SemiProduct.class);

        for (SemiProduct semiProduct : semiProductList) {
            for (Language language : List.of(Language.EN, Language.DE, Language.RW, Language.ES)) {
                SemiProductTranslation semiProductTranslation = new SemiProductTranslation();
                semiProductTranslation.setName(semiProduct.getName());
                semiProductTranslation.setDescription(semiProduct.getDescription());
                semiProductTranslation.setLanguage(language);
                semiProductTranslation.setSemiProduct(semiProduct);
                em.persist(semiProductTranslation);
            }
        }
    }
}
