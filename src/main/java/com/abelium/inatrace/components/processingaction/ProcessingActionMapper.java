package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionProcessingEvidenceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for ProcessingAction entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class ProcessingActionMapper {

	private ProcessingActionMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiProcessingAction toApiProcessingAction(ProcessingAction entity) {

		// Simplest apiProcessingAction object
		ApiProcessingAction apiProcessingAction = new ApiProcessingAction();
		apiProcessingAction.setId(entity.getId());
		apiProcessingAction.setName(entity.getName());
		apiProcessingAction.setDescription(entity.getDescription());
		apiProcessingAction.setPrefix(entity.getPrefix());
		apiProcessingAction.setRepackedOutputs(entity.getRepackedOutputs());
		apiProcessingAction.setMaxOutputWeight(entity.getMaxOutputWeight());
		apiProcessingAction.setPublicTimelineLabel(entity.getPublicTimelineLabel());
		apiProcessingAction.setPublicTimelineLocation(entity.getPublicTimelineLocation());
		apiProcessingAction.setPublicTimelineIconType(entity.getPublicTimelineIconType());
		apiProcessingAction.setType(entity.getType());
		
		ApiCompanyBase apiCompany = new ApiCompanyBase();
		apiCompany.setId(entity.getCompany().getId());
		apiCompany.setName(entity.getCompany().getName());
		
		ApiSemiProduct apiInputSemiProduct = new ApiSemiProduct();
		apiInputSemiProduct.setId(entity.getInputSemiProduct().getId());
		apiInputSemiProduct.setName(entity.getInputSemiProduct().getName());
		
		ApiSemiProduct apiOutputSemiProduct = new ApiSemiProduct();
		apiOutputSemiProduct.setId(entity.getOutputSemiProduct().getId());
		apiOutputSemiProduct.setName(entity.getOutputSemiProduct().getName());
		
		List<ApiProcessingEvidenceType> apiRequiredDocumentTypes = new ArrayList<>();
		
		// Get list of association entities
		List<ProcessingActionProcessingEvidenceType> processingActionProcessingEvidenceTypes = entity.getRequiredDocumentTypes();
		processingActionProcessingEvidenceTypes.forEach(
			processingActionProcessingEvidenceType -> {
				
				ApiProcessingEvidenceType apiRequiredDocumentType = new ApiProcessingEvidenceType();
				apiRequiredDocumentType.setId(processingActionProcessingEvidenceType.getProcessingEvidenceType().getId());
				apiRequiredDocumentType.setLabel(processingActionProcessingEvidenceType.getProcessingEvidenceType().getLabel());
				apiRequiredDocumentTypes.add(apiRequiredDocumentType);
				
			}
		);
		
		// Fill out objects
		apiProcessingAction.setCompany(apiCompany);
		apiProcessingAction.setInputSemiProduct(apiInputSemiProduct);
		apiProcessingAction.setOutputSemiProduct(apiOutputSemiProduct);
		apiProcessingAction.setRequiredDocumentTypes(apiRequiredDocumentTypes);

		return apiProcessingAction;
	}
}
