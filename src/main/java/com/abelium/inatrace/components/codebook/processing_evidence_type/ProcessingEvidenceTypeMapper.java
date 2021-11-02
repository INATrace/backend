package com.abelium.inatrace.components.codebook.processing_evidence_type;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceTypeTranslation;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceTypeTranslation;
import com.abelium.inatrace.types.Language;

import java.util.stream.Collectors;

/**
 * Mapper for ProcessingEvidenceType entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class ProcessingEvidenceTypeMapper {

	private ProcessingEvidenceTypeMapper () {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Mapping the base entity attributes - no associations are included.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiProcessingEvidenceType toApiProcessingEvidenceTypeBase(ProcessingEvidenceType entity, Language language) {

		if (entity == null) {
			return null;
		}

		ProcessingEvidenceTypeTranslation translation = entity.getTranslations().stream().filter(processingEvidenceTypeTranslation ->
				processingEvidenceTypeTranslation.getLanguage().equals(language)
		).findFirst().orElse(new ProcessingEvidenceTypeTranslation());

		if (translation.getLabel() == null || translation.getLabel().equals("")) {
			translation = entity.getTranslations().stream().filter(processingEvidenceTypeTranslation ->
					processingEvidenceTypeTranslation.getLanguage().equals(Language.EN)).findFirst().orElse(new ProcessingEvidenceTypeTranslation());
		}

		ApiProcessingEvidenceType apiProcessingEvidenceType = new ApiProcessingEvidenceType();
		apiProcessingEvidenceType.setId(entity.getId());
		apiProcessingEvidenceType.setCode(entity.getCode());
		apiProcessingEvidenceType.setLabel(translation.getLabel());
		apiProcessingEvidenceType.setType(entity.getType());

		return apiProcessingEvidenceType;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiProcessingEvidenceType toApiProcessingEvidenceType(ProcessingEvidenceType entity, Language language) {

		ApiProcessingEvidenceType apiProcessingEvidenceType = ProcessingEvidenceTypeMapper.toApiProcessingEvidenceTypeBase(
				entity, language);

		if (apiProcessingEvidenceType == null) {
			return null;
		}

		apiProcessingEvidenceType.setProvenance(entity.getProvenance());
		apiProcessingEvidenceType.setFairness(entity.getFairness());
		apiProcessingEvidenceType.setQuality(entity.getQuality());
		apiProcessingEvidenceType.setTranslations(entity.getTranslations().stream().map(ProcessingEvidenceTypeMapper::toApiProcessingEvidenceTypeTranslation).collect(Collectors.toList()));

		return apiProcessingEvidenceType;
	}

	public static ApiProcessingEvidenceTypeTranslation toApiProcessingEvidenceTypeTranslation(ProcessingEvidenceTypeTranslation processingEvidenceTypeTranslation) {
		ApiProcessingEvidenceTypeTranslation apiProcessingEvidenceTypeTranslation = new ApiProcessingEvidenceTypeTranslation();
		apiProcessingEvidenceTypeTranslation.setLabel(processingEvidenceTypeTranslation.getLabel());
		apiProcessingEvidenceTypeTranslation.setLanguage(processingEvidenceTypeTranslation.getLanguage());
		return apiProcessingEvidenceTypeTranslation;
	}
}
