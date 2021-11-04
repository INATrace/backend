package com.abelium.inatrace.components.codebook.processingevidencefield;

import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceFieldTranslation;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceFieldTranslation;
import com.abelium.inatrace.types.Language;

import java.util.stream.Collectors;

/**
 * Mapper for ProcessingEvidenceField entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class ProcessingEvidenceFieldMapper {

	private ProcessingEvidenceFieldMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiProcessingEvidenceField toApiProcessingEvidenceField(ProcessingEvidenceField entity, Language language) {

		if (entity == null) {
			return null;
		}

		ProcessingEvidenceFieldTranslation translation = entity.getTranslations()
				.stream()
				.filter(processingEvidenceFieldTranslation -> processingEvidenceFieldTranslation.getLanguage().equals(language))
				.findFirst()
				.orElse(new ProcessingEvidenceFieldTranslation());

		if (translation.getLabel() == null || translation.getLabel().equals("")) {
			translation = entity.getTranslations()
					.stream()
					.filter(processingEvidenceFieldTranslation -> processingEvidenceFieldTranslation.getLanguage().equals(Language.EN))
					.findFirst()
					.orElse(new ProcessingEvidenceFieldTranslation());
		}

		// ApiProcessingEvidenceField object
		ApiProcessingEvidenceField apiProcessingEvidenceField = new ApiProcessingEvidenceField();
		apiProcessingEvidenceField.setId(entity.getId());
		apiProcessingEvidenceField.setFieldName(entity.getFieldName());
		apiProcessingEvidenceField.setLabel(translation.getLabel());
		apiProcessingEvidenceField.setType(entity.getType());
		
		return apiProcessingEvidenceField;
	}

	public static ApiProcessingEvidenceField toApiProcessingEvidenceFieldDetails(ProcessingEvidenceField entity, Language language) {
		if (entity == null) {
			return null;
		}

		ApiProcessingEvidenceField apiProcessingEvidenceField = toApiProcessingEvidenceField(entity, language);
		apiProcessingEvidenceField.setTranslations(entity
				.getTranslations()
				.stream()
				.map(ProcessingEvidenceFieldMapper::toApiProcessingEvidenceFieldTranslation)
				.collect(Collectors.toList()));

		return apiProcessingEvidenceField;
	}

	public static ApiProcessingEvidenceFieldTranslation toApiProcessingEvidenceFieldTranslation(ProcessingEvidenceFieldTranslation translation) {
		ApiProcessingEvidenceFieldTranslation apiProcessingEvidenceFieldTranslation = new ApiProcessingEvidenceFieldTranslation();
		apiProcessingEvidenceFieldTranslation.setLabel(translation.getLabel());
		apiProcessingEvidenceFieldTranslation.setLanguage(translation.getLanguage());
		return apiProcessingEvidenceFieldTranslation;
	}
}
