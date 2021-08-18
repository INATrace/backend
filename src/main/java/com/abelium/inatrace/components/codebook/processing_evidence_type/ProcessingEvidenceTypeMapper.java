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

	public static ApiProcessingEvidenceType toApiProcessingEvidenceType(ProcessingEvidenceType entity) {

		ApiProcessingEvidenceType apiProcessingEvidenceType = new ApiProcessingEvidenceType();
		apiProcessingEvidenceType.setId(entity.getId());
		apiProcessingEvidenceType.setCode(entity.getCode());
		apiProcessingEvidenceType.setLabel(entity.getLabel());
		apiProcessingEvidenceType.setType(entity.getType());
		apiProcessingEvidenceType.setProvenance(entity.getProvenance());
		apiProcessingEvidenceType.setFairness(entity.getFairness());
		apiProcessingEvidenceType.setQuality(entity.getQuality());
		apiProcessingEvidenceType.setRequired(entity.getRequired());
		apiProcessingEvidenceType.setRequiredOnQuote(entity.getRequiredOnQuote());
		apiProcessingEvidenceType.setRequiredOneOfGroupIdForQuote(entity.getRequiredOneOfGroupIdForQuote());

		return apiProcessingEvidenceType;
	}
}
