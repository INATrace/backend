package com.abelium.inatrace.components.codebook.processing_evidence_type;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;

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
	public static ApiProcessingEvidenceType toApiProcessingEvidenceTypeBase(ProcessingEvidenceType entity) {

		if (entity == null) {
			return null;
		}

		ApiProcessingEvidenceType apiProcessingEvidenceType = new ApiProcessingEvidenceType();
		apiProcessingEvidenceType.setId(entity.getId());
		apiProcessingEvidenceType.setCode(entity.getCode());
		apiProcessingEvidenceType.setLabel(entity.getLabel());
		apiProcessingEvidenceType.setType(entity.getType());

		return apiProcessingEvidenceType;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiProcessingEvidenceType toApiProcessingEvidenceType(ProcessingEvidenceType entity) {

		ApiProcessingEvidenceType apiProcessingEvidenceType = ProcessingEvidenceTypeMapper.toApiProcessingEvidenceTypeBase(
				entity);

		if (apiProcessingEvidenceType == null) {
			return null;
		}

		apiProcessingEvidenceType.setProvenance(entity.getProvenance());
		apiProcessingEvidenceType.setFairness(entity.getFairness());
		apiProcessingEvidenceType.setQuality(entity.getQuality());

		return apiProcessingEvidenceType;
	}
}
