package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionOutputSemiProduct;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class V2023_02_09_09_00__Migrate_Processing_Actions_Output_SemiProducts implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        List<ProcessingAction> processingActionList = Queries.getAll(em, ProcessingAction.class);
        for (ProcessingAction processingAction : processingActionList) {

            // If Processing action has output semi-product defined, create a new connecting entity between the Processing action and the semi-product
            if (processingAction.getOutputSemiProduct() != null) {

                ProcessingActionOutputSemiProduct paOSM = new ProcessingActionOutputSemiProduct();
                paOSM.setProcessingAction(processingAction);
                paOSM.setOutputSemiProduct(processingAction.getOutputSemiProduct());
                processingAction.getOutputSemiProducts().add(paOSM);
            }
        }
    }
}
