package com.abelium.INATrace.db.migrations;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.core.env.Environment;
import com.abelium.INATrace.components.flyway.JpaMigration;
import com.abelium.INATrace.db.entities.ProductLabel;
import com.abelium.INATrace.db.entities.ProductLabelContent;
import com.abelium.INATrace.tools.Queries;

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
        	em.persist(plc.getComparisonOfPrice());
        	em.persist(plc);
        	pl.setContent(plc);
        }
    }
}
