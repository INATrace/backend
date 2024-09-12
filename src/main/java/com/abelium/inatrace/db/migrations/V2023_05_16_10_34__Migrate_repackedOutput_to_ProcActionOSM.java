package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2023_05_16_10_34__Migrate_repackedOutput_to_ProcActionOSM implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        List<ProcessingAction> processingActions = Queries.getAll(em, ProcessingAction.class);

        for (ProcessingAction processingAction : processingActions) {
            if (processingAction.getOutputFinalProduct() != null) {
                continue;
            }

            processingAction.getOutputSemiProducts().stream().findFirst().ifPresent(paOSM -> {
                paOSM.setRepackedOutput(processingAction.getRepackedOutputFinalProducts());
                paOSM.setMaxOutputWeight(processingAction.getMaxOutputWeight());
                processingAction.setRepackedOutputFinalProducts(null);
                processingAction.setMaxOutputWeight(null);
            });
        }
    }
}
