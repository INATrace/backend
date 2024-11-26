package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceFieldTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

public class V2021_11_02_10_07__Update_Processing_Evidence_Field_Translations implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<ProcessingEvidenceField> processingEvidenceFieldList = Queries.getAll(em, ProcessingEvidenceField.class);

        for (ProcessingEvidenceField processingEvidenceField : processingEvidenceFieldList) {
            for (Language language : List.of(Language.EN, Language.DE, Language.RW, Language.ES)) {
                ProcessingEvidenceFieldTranslation processingEvidenceFieldTranslation = new ProcessingEvidenceFieldTranslation();
                processingEvidenceFieldTranslation.setLabel(processingEvidenceField.getLabel());
                processingEvidenceFieldTranslation.setLanguage(language);
                processingEvidenceFieldTranslation.setProcessingEvidenceField(processingEvidenceField);
                em.persist(processingEvidenceFieldTranslation);
            }
        }
    }
}
