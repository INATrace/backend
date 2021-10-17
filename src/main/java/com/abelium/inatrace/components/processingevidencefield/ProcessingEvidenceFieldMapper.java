package com.abelium.inatrace.components.processingevidencefield;

import com.abelium.inatrace.components.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;

/**
 * Mapper for ProcessingEvidenceField entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class ProcessingEvidenceFieldMapper {

	private ProcessingEvidenceFieldMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiProcessingEvidenceField toApiProcessingEvidenceField(ProcessingEvidenceField entity) {

		// ApiProcessingEvidenceField object
		ApiProcessingEvidenceField apiProcessingEvidenceField = new ApiProcessingEvidenceField();
		apiProcessingEvidenceField.setId(entity.getId());
		apiProcessingEvidenceField.setFieldName(entity.getFieldName());
		apiProcessingEvidenceField.setLabel(entity.getLabel());
		apiProcessingEvidenceField.setFileMultiplicity(entity.getFileMultiplicity());
		apiProcessingEvidenceField.setType(entity.getType());
		
		return apiProcessingEvidenceField;
	}
}
