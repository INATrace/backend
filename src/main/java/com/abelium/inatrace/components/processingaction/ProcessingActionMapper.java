package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingActionOutputSemiProduct;
import com.abelium.inatrace.components.processingactiontranslation.ProcessingActionTranslationMapper;
import com.abelium.inatrace.components.product.ProductApiTools;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceFieldTranslation;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceTypeTranslation;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilityTranslation;
import com.abelium.inatrace.db.entities.processingaction.*;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.types.Language;
import org.apache.commons.lang3.BooleanUtils;

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

		// Map the value chains
		List<ApiValueChain> apiValueChains = new ArrayList<>();
		entity.getProcessingActionsValueChains().forEach(vcpa -> {

			ValueChain valueChain = vcpa.getValueChain();

			// only return some basic data
			ApiValueChain apiValueChain = new ApiValueChain();
			apiValueChain.setId(valueChain.getId());
			apiValueChain.setName(valueChain.getName());
			apiValueChain.setDescription(valueChain.getDescription());

			apiValueChains.add(apiValueChain);
		});
		apiProcessingAction.setValueChains(apiValueChains);

		// Set the translated name and description
		entity.getProcessingActionTranslations().stream()
				.filter(pat -> pat.getLanguage().equals(language)).findAny().ifPresent(pat -> {
					apiProcessingAction.setName(pat.getName());
					apiProcessingAction.setDescription(pat.getDescription());
					apiProcessingAction.setLanguage(pat.getLanguage());
				});

		apiProcessingAction.setSortOrder(entity.getSortOrder());
		apiProcessingAction.setPrefix(entity.getPrefix());
		apiProcessingAction.setRepackedOutputFinalProducts(entity.getRepackedOutputFinalProducts());
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

		// Map the input semi-product
		apiProcessingAction.setInputSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getInputSemiProduct(), ApiSemiProduct.class, language));

		// Map the output semi-products
		apiProcessingAction.setOutputSemiProducts(entity.getOutputSemiProducts()
				.stream()
				.map(paOSM -> ProcessingActionMapper.toApiProcessingActionOSM(paOSM, language))
				.collect(Collectors.toList())
		);

		// Map the input and output final products
		apiProcessingAction.setInputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getInputFinalProduct()));
		apiProcessingAction.setOutputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getOutputFinalProduct()));

		// Set the estimated output quantity per unit
		apiProcessingAction.setEstimatedOutputQuantityPerUnit(entity.getEstimatedOutputQuantityPerUnit());

		// Map the final product for which a QR code should be generated
		apiProcessingAction.setQrCodeForFinalProduct(ProductApiTools.toApiFinalProduct(entity.getQrCodeForFinalProduct()));

		// Processing evidence fields
		apiProcessingAction.setRequiredEvidenceFields(ProcessingActionMapper.mapProcessingEvidenceFields(entity, language));

		List<ApiProcessingEvidenceType> apiRequiredDocumentTypes = new ArrayList<>();

		// Processing evidence types
		List<ProcessingActionPET> processingActionProcessingEvidenceTypes = entity.getRequiredDocumentTypes().stream().toList();
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

		// Map the supported facilities for this
		List<ApiFacility> supportedFacilities = new ArrayList<>();
		List<ProcessingActionFacility> processingActionFacilities = entity.getProcessingActionFacilities().stream().toList();
		processingActionFacilities.forEach(processingActionFacility -> {

			// Get the translated facility name
			Facility facility = processingActionFacility.getFacility();
			FacilityTranslation facilityTranslation = facility.getFacilityTranslations()
					.stream()
					.filter(ft -> ft.getLanguage().equals(language))
					.findFirst()
					.orElseGet(() -> facility.getFacilityTranslations()
							.stream()
							.filter(ft -> ft.getLanguage().equals(Language.EN))
							.findAny()
							.orElse(new FacilityTranslation()));

			ApiFacility supportedFacility = new ApiFacility();
			supportedFacility.setId(processingActionFacility.getFacility().getId());
			supportedFacility.setName(facilityTranslation.getName());
			supportedFacilities.add(supportedFacility);
		});
		apiProcessingAction.setSupportedFacilities(supportedFacilities);

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

	public static ApiProcessingAction toApiProcessingActionHistory(ProcessingAction entity, Language language) {

		if (entity == null) {
			return null;
		}

		ApiProcessingAction apiProcessingAction = new ApiProcessingAction();
		apiProcessingAction.setId(entity.getId());

		// Set the translated name and description
		entity.getProcessingActionTranslations().stream()
				.filter(pat -> pat.getLanguage().equals(language)).findAny().ifPresent(pat -> apiProcessingAction.setName(pat.getName()));

		apiProcessingAction.setPublicTimelineLabel(entity.getPublicTimelineLabel());
		apiProcessingAction.setPublicTimelineIconType(entity.getPublicTimelineIconType());
		apiProcessingAction.setType(entity.getType());

		// Map the input semi-product
		apiProcessingAction.setInputSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getInputSemiProduct(), ApiSemiProduct.class, language));

		// Map the output semi-products
		apiProcessingAction.setOutputSemiProducts(entity.getOutputSemiProducts()
				.stream()
				.map(paOSM -> ProcessingActionMapper.toApiProcessingActionOSM(paOSM, language))
				.collect(Collectors.toList())
		);

		// Map the input and output final products
		apiProcessingAction.setInputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getInputFinalProduct()));
		apiProcessingAction.setOutputFinalProduct(ProductApiTools.toApiFinalProduct(entity.getOutputFinalProduct()));

		// Map the processing evidence fields
		apiProcessingAction.setRequiredEvidenceFields(ProcessingActionMapper.mapProcessingEvidenceFields(entity, language));

		return apiProcessingAction;
	}

	public static ApiProcessingActionOutputSemiProduct toApiProcessingActionOSM(ProcessingActionOutputSemiProduct entity, Language language) {

		ApiProcessingActionOutputSemiProduct apiPaOSM = SemiProductMapper.toApiSemiProduct(
				entity.getOutputSemiProduct(), ApiProcessingActionOutputSemiProduct.class, language);
		apiPaOSM.setRepackedOutput(entity.getRepackedOutput());
		if (BooleanUtils.isTrue(entity.getRepackedOutput())) {
			apiPaOSM.setMaxOutputWeight(entity.getMaxOutputWeight());
		}

		return apiPaOSM;
	}

	private static List<ApiProcessingEvidenceField> mapProcessingEvidenceFields(ProcessingAction entity, Language language) {

		List<ApiProcessingEvidenceField> apiRequiredEvidenceFields = new ArrayList<>();
		List<ProcessingActionPEF> processingActionProcessingEvidenceFields = entity.getProcessingEvidenceFields().stream().toList();
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

		return apiRequiredEvidenceFields;
	}

}
