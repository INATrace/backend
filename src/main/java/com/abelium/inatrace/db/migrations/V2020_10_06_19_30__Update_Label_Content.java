package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.product.ProductLabel;
import com.abelium.inatrace.db.entities.product.ProductLabelContent;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2020_10_06_19_30__Update_Label_Content implements JpaMigration {
    
    public void migrate(EntityManager em, Environment environment) throws Exception {
    	List<ProductLabel> labels = Queries.getAll(em, ProductLabel.class);
    	
        for (ProductLabel pl : labels) {
        	if (pl.getContent() != null) continue;
        	
        	ProductLabelContent plc = ProductLabelContent.fromProduct(pl.getProduct());
        	em.persist(plc.getProcess());
        	em.persist(plc.getResponsibility());
        	em.persist(plc.getSustainability());
        	em.persist(plc.getSettings());
        	em.persist(plc);
        	pl.setContent(plc);
        }
    }
}
