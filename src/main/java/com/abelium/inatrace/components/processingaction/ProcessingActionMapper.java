package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.processingactiontranslation.ProcessingActionTranslationMapper;
import com.abelium.inatrace.components.product.ProductApiTools;
import com.abelium.inatrace.components.value_chain.ValueChainMapper;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceFieldTranslation;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceTypeTranslation;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPEF;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPET;
import com.abelium.inatrace.types.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for ProcessingAction entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class ProcessingActionMapper {

	private ProcessingActionMapper() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ApiProcessingAction toApiProcessingAction(ProcessingAction entity, Language language) {

		ApiProcessingAction apiProcessingAction = new ApiProcessingAction();
		apiProcessingAction.setId(entity.getId());

		// Map the value chain
		apiProcessingAction.setValueChain(ValueChainMapper.toApiValueChainBase(entity.getValueChain()));

		// Set the translated name and description
		entity.getProcessingActionTranslations().stream()
				.filter(pat -> pat.getLanguage().equals(language)).findAny().ifPresent(pat -> {
					apiProcessingAction.setName(pat.getName());
					apiProcessingAction.setDescription(pat.getDescription());
					apiProcessingAction.setLanguage(pat.getLanguage());
				});

		apiProcessingAction.setSortOrder(entity.getSortOrder());
		apiProcessingAction.setPrefix(entity.getPrefix());
		apiProcessingAction.setRepackedOutputs(entity.getRepackedOutputs());
		apiProcessingAction.setMaxOutputWeight(entity.getMaxOutputWeight());
		apiProcessingAction.setPublicTimelineLabel(entity.getPublicTimelineLabel());
		apiProcessingAction.setPublicTimelineLocation(entity.getPublicTimelineLocation());
		apiProcessingAction.setPublicTimelineIconType(entity.getPublicTimelineIconType());
		apiProcessingAction.setType(entity.getType());
		apiProcessingAction.setFinalProductAction(entity.getFinalProductAction());
		
		ApiCompanyBase apiCompany = new ApiCompanyBase();
		apiCompany.setId(entity.getCompany().getId());
		apiCompany.setName(entity.getCompany().getName());
		apiProcessingAction.setCompany(apiCompany);

		// Map the input and output semi-products
		apiProcessingAction.setInputSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getInputSemiProduct(), language));
		apiProcessingAction.setOutputSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getOutputSemiProduct(), language));

		// Map the input and output final products
		apiProcessingAction.setInputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getInputFinalProduct()));
		apiProcessingAction.setOutputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getOutputFinalProduct()));

		// Map the final prodcut for which a QR code should be generated
		apiProcessingAction.setQrCodeForFinalProduct(ProductApiTools.toApiFinalProduct(entity.getQrCodeForFinalProduct()));

		// Processing evidence fields
		List<ApiProcessingEvidenceField> apiRequiredEvidenceFields = new ArrayList<>();
		List<ProcessingActionPEF> processingActionProcessingEvidenceFields = entity.getProcessingEvidenceFields();
		processingActionProcessingEvidenceFields.forEach(
			processingActionProcessingEvidenceField -> {

				// Get the translation for the request lang - if value is not present return the EN value which is required
				ProcessingEvidenceField procEvidenceField = processingActionProcessingEvidenceField.getProcessingEvidenceField();
				ProcessingEvidenceFieldTranslation translation = procEvidenceField.getTranslations().stream()
						.filter(t -> t.getLanguage().equals(language)).findFirst().orElseGet(
								() -> procEvidenceField.getTranslations().stream()
										.filter(t -> t.getLanguage().equals(Language.EN)).findAny()
										.orElse(new ProcessingEvidenceFieldTranslation()));

				ApiProcessingEvidenceField apiProcessingEvidenceField = new ApiProcessingEvidenceField();
				apiProcessingEvidenceField.setId(procEvidenceField.getId());
				apiProcessingEvidenceField.setFieldName(procEvidenceField.getFieldName());
				apiProcessingEvidenceField.setLabel(translation.getLabel());
				apiProcessingEvidenceField.setType(procEvidenceField.getType());
				apiProcessingEvidenceField.setMandatory(processingActionProcessingEvidenceField.getMandatory());
				apiProcessingEvidenceField.setRequiredOnQuote(processingActionProcessingEvidenceField.getRequiredOnQuote());
				apiRequiredEvidenceFields.add(apiProcessingEvidenceField);
			}
		);
		apiProcessingAction.setRequiredEvidenceFields(apiRequiredEvidenceFields);
		
		List<ApiProcessingEvidenceType> apiRequiredDocumentTypes = new ArrayList<>();
		
		// Processing evidence types
		List<ProcessingActionPET> processingActionProcessingEvidenceTypes = entity.getRequiredDocumentTypes();
		processingActionProcessingEvidenceTypes.forEach(
			processingActionProcessingEvidenceType -> {

				// Get the translation for the request lang - if value is not present return the EN value which is required
				ProcessingEvidenceType procEvidenceType = processingActionProcessingEvidenceType.getProcessingEvidenceType();
				ProcessingEvidenceTypeTranslation translation = procEvidenceType.getTranslations().stream()
						.filter(t -> t.getLanguage().equals(language)).findFirst().orElseGet(
								() -> procEvidenceType.getTranslations().stream()
										.filter(t -> t.getLanguage().equals(Language.EN)).findAny()
										.orElse(new ProcessingEvidenceTypeTranslation()));
				
				ApiProcessingEvidenceType apiRequiredDocumentType = new ApiProcessingEvidenceType();
				apiRequiredDocumentType.setId(procEvidenceType.getId());
				apiRequiredDocumentType.setCode(procEvidenceType.getCode());
				apiRequiredDocumentType.setLabel(translation.getLabel());
				apiRequiredDocumentType.setMandatory(processingActionProcessingEvidenceType.getMandatory());
				apiRequiredDocumentType.setRequiredOnQuote(processingActionProcessingEvidenceType.getRequiredOnQuote());
				apiRequiredDocumentType.setRequiredOneOfGroupIdForQuote(processingActionProcessingEvidenceType.getRequiredOneOfGroupIdForQuote());
				apiRequiredDocumentTypes.add(apiRequiredDocumentType);
			}
		);
		apiProcessingAction.setRequiredDocumentTypes(apiRequiredDocumentTypes);

		return apiProcessingAction;
	}

	public static ApiProcessingAction toApiProcessingActionDetail(ProcessingAction entity, Language language) {

		ApiProcessingAction apiProcessingAction = toApiProcessingAction(entity, language);
		apiProcessingAction.setTranslations(entity.getProcessingActionTranslations()
				.stream()
				.map(ProcessingActionTranslationMapper::toApiProcessingActionTranslation)
				.collect(Collectors.toList()));

		return apiProcessingAction;
	}

}
