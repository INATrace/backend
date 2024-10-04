package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceTypeTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2021_11_02_13_31__Update_Processing_Evidence_Type_Translations implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<ProcessingEvidenceType> processingEvidenceTypeList = Queries.getAll(em, ProcessingEvidenceType.class);

        for (ProcessingEvidenceType processingEvidenceType : processingEvidenceTypeList) {
            for (Language language : List.of(Language.EN, Language.DE, Language.RW, Language.ES)) {
                ProcessingEvidenceTypeTranslation processingEvidenceTypeTranslation = new ProcessingEvidenceTypeTranslation();
                processingEvidenceTypeTranslation.setLabel(processingEvidenceType.getLabel());
                processingEvidenceTypeTranslation.setLanguage(language);
                processingEvidenceTypeTranslation.setProcessingEvidenceType(processingEvidenceType);
                em.persist(processingEvidenceTypeTranslation);
            }
        }
    }
}
