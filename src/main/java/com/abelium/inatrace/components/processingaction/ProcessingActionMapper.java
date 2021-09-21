package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPEF;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPET;

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

		System.out.println("@@@Translations size: " + entity.getProcessingActionTranslations().size());

		// Simplest apiProcessingAction object
		ApiProcessingAction apiProcessingAction = new ApiProcessingAction();
		apiProcessingAction.setId(entity.getId());
		apiProcessingAction.setName(entity
				.getProcessingActionTranslations()
				.stream()
				.findFirst()
				.orElse(null)
				.getName());
		apiProcessingAction.setDescription(entity
				.getProcessingActionTranslations()
				.stream()
				.findFirst()
				.orElse(null)
				.getDescription());
		apiProcessingAction.setLanguage(entity
				.getProcessingActionTranslations()
				.stream()
				.findFirst()
				.orElse(null)
				.getLanguage());
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
		if(entity.getOutputSemiProduct() != null) {
			apiOutputSemiProduct.setId(entity.getOutputSemiProduct().getId());
			apiOutputSemiProduct.setName(entity.getOutputSemiProduct().getName());
		}
		
		List<ApiProcessingEvidenceField> apiRequiredEvidenceFields = new ArrayList<>();
		List<ProcessingActionPEF> processingActionProcessingEvidenceFields = entity.getProcessingEvidenceFields();
		processingActionProcessingEvidenceFields.forEach(
			processingActionProcessingEvidenceField -> {

				ApiProcessingEvidenceField apiProcessingEvidenceField = new ApiProcessingEvidenceField();
				apiProcessingEvidenceField.setId(processingActionProcessingEvidenceField.getProcessingEvidenceField().getId());
				apiProcessingEvidenceField.setLabel(processingActionProcessingEvidenceField.getProcessingEvidenceField().getLabel());
				apiProcessingEvidenceField.setMandatory(processingActionProcessingEvidenceField.getMandatory());
				apiProcessingEvidenceField.setRequiredOnQuote(processingActionProcessingEvidenceField.getRequiredOnQuote());
				apiRequiredEvidenceFields.add(apiProcessingEvidenceField);
			}
		);
		
		List<ApiProcessingEvidenceType> apiRequiredDocumentTypes = new ArrayList<>();
		
		// Get list of association entities
		List<ProcessingActionPET> processingActionProcessingEvidenceTypes = entity.getRequiredDocumentTypes();
		processingActionProcessingEvidenceTypes.forEach(
			processingActionProcessingEvidenceType -> {
				
				ApiProcessingEvidenceType apiRequiredDocumentType = new ApiProcessingEvidenceType();
				apiRequiredDocumentType.setId(processingActionProcessingEvidenceType.getProcessingEvidenceType().getId());
				apiRequiredDocumentType.setLabel(processingActionProcessingEvidenceType.getProcessingEvidenceType().getLabel());
				apiRequiredDocumentType.setMandatory(processingActionProcessingEvidenceType.getMandatory());
				apiRequiredDocumentType.setRequiredOnQuote(processingActionProcessingEvidenceType.getRequiredOnQuote());
				apiRequiredDocumentType.setRequiredOneOfGroupIdForQuote(processingActionProcessingEvidenceType.getRequiredOneOfGroupIdForQuote());
				apiRequiredDocumentTypes.add(apiRequiredDocumentType);
				
			}
		);
		
		// Fill out objects
		apiProcessingAction.setCompany(apiCompany);
		apiProcessingAction.setInputSemiProduct(apiInputSemiProduct);
		apiProcessingAction.setOutputSemiProduct(apiOutputSemiProduct);
		apiProcessingAction.setRequiredDocumentTypes(apiRequiredDocumentTypes);
		apiProcessingAction.setRequiredEvidenceFields(apiRequiredEvidenceFields);

		return apiProcessingAction;
	}

}
