package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processing_evidence_type.ProcessingEvidenceTypeService;
import com.abelium.inatrace.components.codebook.processingevidencefield.ProcessingEvidenceFieldService;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.company.CompanyQueries;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.product.FinalProductService;
import com.abelium.inatrace.components.value_chain.ValueChainService;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingaction.*;
import com.abelium.inatrace.db.entities.product.FinalProduct;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.UserRole;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for processing action entity.
 *
 * @author Rene Flores, Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class ProcessingActionService extends BaseService {

	private final CompanyQueries companyQueries;

	private final ValueChainService valueChainService;

	private final SemiProductService semiProductService;

	private final FinalProductService finalProductService;

	private final ProcessingEvidenceTypeService processingEvidenceTypeService;

	private final ProcessingEvidenceFieldService processingEvidenceFieldService;

	private final FacilityService facilityService;

	@Autowired
	public ProcessingActionService(CompanyQueries companyQueries,
								   ValueChainService valueChainService,
	                               SemiProductService semiProductService,
								   FinalProductService finalProductService,
	                               ProcessingEvidenceTypeService processingEvidenceTypeService,
	                               ProcessingEvidenceFieldService processingEvidenceFieldService,
	                               FacilityService facilityService) {
		this.companyQueries = companyQueries;
		this.valueChainService = valueChainService;
		this.semiProductService = semiProductService;
		this.finalProductService = finalProductService;
		this.processingEvidenceTypeService = processingEvidenceTypeService;
		this.processingEvidenceFieldService = processingEvidenceFieldService;
		this.facilityService = facilityService;
	}

	public ApiPaginatedList<ApiProcessingAction> listProcessingActions(ApiPaginatedRequest request, Language language) {

		TypedQuery<ProcessingAction> processingActionsQuery = 
				em.createNamedQuery("ProcessingAction.listProcessingActions", ProcessingAction.class)
					.setParameter("language", language)
					.setFirstResult(request.getOffset())
					.setMaxResults(request.getLimit());

		List<ProcessingAction> processingActions = processingActionsQuery.getResultList();

		Long count = em.createNamedQuery("ProcessingAction.countProcessingActions", Long.class)
			.setParameter("language", language)
			.getSingleResult();

		return new ApiPaginatedList<>(
			processingActions
				.stream()
				.map(processingAction -> ProcessingActionMapper.toApiProcessingAction(processingAction, language))
				.collect(Collectors.toList()), count);
	}

	public ApiProcessingAction getProcessingAction(Long id, Language language) throws ApiException {
		
		ProcessingAction processingAction = fetchProcessingAction(id);
		return ProcessingActionMapper.toApiProcessingAction(processingAction, language);
	}

	public ApiProcessingAction getProcessingActionDetail(Long id, Language language) throws ApiException {

		ProcessingAction processingAction = fetchProcessingAction(id);
		return ProcessingActionMapper.toApiProcessingActionDetail(processingAction, language);
	}

	@Transactional
	public ApiBaseEntity createOrUpdateProcessingAction(ApiProcessingAction apiProcessingAction, CustomUserDetails user) throws ApiException {

		ProcessingAction entity = null;
		if (apiProcessingAction.getId() != null) {
			entity = fetchProcessingAction(apiProcessingAction.getId());
			if (
					user.getUserRole() != UserRole.ADMIN &&
					entity.getCompany().getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
				throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in current company");
			}
		} else {
			entity = new ProcessingAction();
		}

		// Validate Processing action data
		validateProcessingAction(apiProcessingAction);

		// Set the value chain
		entity.setValueChain(valueChainService.fetchValueChain(apiProcessingAction.getValueChain().getId()));

		entity.setSortOrder(apiProcessingAction.getSortOrder());
		entity.setPrefix(apiProcessingAction.getPrefix());
		entity.setRepackedOutputs(apiProcessingAction.getRepackedOutputs());
		entity.setMaxOutputWeight(apiProcessingAction.getMaxOutputWeight());
		entity.setPublicTimelineLabel(apiProcessingAction.getPublicTimelineLabel());
		entity.setPublicTimelineLocation(apiProcessingAction.getPublicTimelineLocation());
		entity.setPublicTimelineIconType(apiProcessingAction.getPublicTimelineIconType());
		entity.setType(apiProcessingAction.getType());

		// If we have shipment or transfer, set the the field denoting if we are dealing with final products
		if (ProcessingActionType.TRANSFER.equals(apiProcessingAction.getType()) ||
				ProcessingActionType.SHIPMENT.equals(apiProcessingAction.getType())) {
			entity.setFinalProductAction(BooleanUtils.toBooleanDefaultIfNull(apiProcessingAction.getFinalProductAction(), false));
		} else {
			entity.setFinalProductAction(Boolean.FALSE);
		}
		
		Company company = companyQueries.fetchCompany(apiProcessingAction.getCompany().getId());
		if (company != null) {
			if(entity.getCompany() != null && !company.getId().equals(entity.getCompany().getId())) {
				if(
						user.getUserRole() != UserRole.ADMIN &&
						company.getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
					throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in updated company!");
				}
			}
			entity.setCompany(company);
		}

		// Set semi-products and final products (depending on the Processing action type)
		setSemiAndFinalProducts(apiProcessingAction, entity);

		// Set the estimated output quantity per unit
		entity.setEstimatedOutputQuantityPerUnit(apiProcessingAction.getEstimatedOutputQuantityPerUnit());

		// Update the translations
		updateProcActionTranslations(apiProcessingAction, entity);
		
		// Create or update required processing evidence fields
		updateRequiredEvidenceFields(apiProcessingAction, entity);

		// Create or update required processing evidence types
		updateRequiredEvidenceTypes(apiProcessingAction, entity);

		// Create or update the supported facilities for this processing action (the facilities where this processing starts)
		updateSupportedFacilities(apiProcessingAction, entity);

		// If action type is GENERATE_QR_CODE, set Final product for QR code
		if (ProcessingActionType.GENERATE_QR_CODE.equals(apiProcessingAction.getType())) {
			entity.setQrCodeForFinalProduct(
					finalProductService.fetchFinalProduct(apiProcessingAction.getQrCodeForFinalProduct().getId()));
		}

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteProcessingAction(Long id, CustomUserDetails user) throws ApiException {

		ProcessingAction processingAction = fetchProcessingAction(id);
		if (
				user.getUserRole() != UserRole.ADMIN &&
				processingAction.getCompany().getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in company");
		}

		em.remove(processingAction);
	}

	public ProcessingAction fetchProcessingAction(Long id) throws ApiException {

		ProcessingAction processingAction = Queries.get(em, ProcessingAction.class, id);
		if (processingAction == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid processing action ID");
		}
		return processingAction;
	}
	
	public ApiPaginatedList<ApiProcessingAction> listProcessingActionsByCompany(Long companyId, Language language,
	                                                                            ApiPaginatedRequest request,
	                                                                            ProcessingActionType actionType,
	                                                                            Boolean onlyFinalProducts) {

		return PaginationTools.createPaginatedResponse(em, request, () ->
						listProcessingActionsByCompanyQuery(companyId, language, actionType, onlyFinalProducts),
				processingAction -> ProcessingActionMapper.toApiProcessingAction(processingAction, language));
	}

	private ProcessingAction listProcessingActionsByCompanyQuery(Long companyId, Language language,
	                                                             ProcessingActionType actionType,
	                                                             Boolean onlyFinalProducts) {

		ProcessingAction processingActionProxy = Torpedo.from(ProcessingAction.class);
		ProcessingActionTranslation procActionTranslation = Torpedo.innerJoin(
				processingActionProxy.getProcessingActionTranslations());

		OnGoingLogicalCondition condition = Torpedo.condition();

		condition = condition.and(procActionTranslation.getLanguage()).eq(language);
		condition = condition.and(processingActionProxy.getCompany().getId()).eq(companyId);

		if (actionType != null) {
			condition = condition.and(processingActionProxy.getType()).eq(actionType);
		}

		if (BooleanUtils.isTrue(onlyFinalProducts)) {
			condition = condition.and(processingActionProxy.getInputFinalProduct()).isNotNull();
			condition = condition.and(processingActionProxy.getOutputFinalProduct()).isNotNull();
		}

		Torpedo.where(condition);

		Torpedo.orderBy(processingActionProxy.getId());
		Torpedo.orderBy(processingActionProxy.getSortOrder());

		return processingActionProxy;
	}

	private void validateProcessingAction(ApiProcessingAction apiProcessingAction) throws ApiException {

		// Validate value chain
		if (apiProcessingAction.getValueChain() == null || apiProcessingAction.getValueChain().getId() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Value chain is required");
		}

		// Validate action type
		if (apiProcessingAction.getType() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Action type is required");
		}

		// Validate that estimated quantity is not provided if we don't have processing action type 'PROCESSING'
		if (!apiProcessingAction.getType().equals(ProcessingActionType.PROCESSING) &&
				apiProcessingAction.getEstimatedOutputQuantityPerUnit() != null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST,
					"Estimated output quantity cannot be provided when action is not 'PROCESSING'");
		}

		switch (apiProcessingAction.getType()) {
			case PROCESSING:

				// Validate input semi-product
				if (apiProcessingAction.getInputSemiProduct() == null || apiProcessingAction.getInputSemiProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Input semi-product is required");
				}

				// Validate output semi-product
				if (apiProcessingAction.getOutputSemiProduct() == null || apiProcessingAction.getOutputSemiProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Output semi-product is required");
				}
				break;

			case FINAL_PROCESSING:

				// Validate input semi-product
				if (apiProcessingAction.getInputSemiProduct() == null || apiProcessingAction.getInputSemiProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Input semi-product is required");
				}

				// Output should be in this case final product
				if (apiProcessingAction.getOutputFinalProduct() == null || apiProcessingAction.getOutputFinalProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Output final product is required");
				}
				break;

			case TRANSFER:
			case SHIPMENT:

				if (BooleanUtils.isTrue(apiProcessingAction.getFinalProductAction())) {

					// Should be provided input final product
					if (apiProcessingAction.getInputFinalProduct() == null || apiProcessingAction.getInputFinalProduct().getId() == null) {
						throw new ApiException(ApiStatus.INVALID_REQUEST, "Input final product is required");
					}

				} else {

					// Should be provided input semi-product
					if (apiProcessingAction.getInputSemiProduct() == null || apiProcessingAction.getInputSemiProduct().getId() == null) {
						throw new ApiException(ApiStatus.INVALID_REQUEST, "Input semi-product is required");
					}
				}
				break;
			case GENERATE_QR_CODE:

				// Validate input semi-product
				if (apiProcessingAction.getInputSemiProduct() == null || apiProcessingAction.getInputSemiProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Input semi-product is required");
				}

				// Validate that the Final product for which the QR code should be generated is provided
				if (apiProcessingAction.getQrCodeForFinalProduct() == null || apiProcessingAction.getQrCodeForFinalProduct().getId() == null) {
					throw new ApiException(ApiStatus.INVALID_REQUEST, "Final product for QR code is required");
				}
		}

		// Make sure English translation is present
		apiProcessingAction.getTranslations()
				.stream()
				.filter(t -> t != null
						&& Language.EN.equals(t.getLanguage())
						&& t.getDescription() != null
						&& t.getName() != null)
				.findFirst()
				.orElseThrow(() -> new ApiException(ApiStatus.INVALID_REQUEST, "English translation is required!"));
	}

	private void setSemiAndFinalProducts(ApiProcessingAction apiProcessingAction, ProcessingAction entity) throws ApiException {

		SemiProduct inputSemiProduct;
		SemiProduct outputSemiProduct;

		FinalProduct inputFinalProduct;
		FinalProduct outputFinalProduct;

		switch (apiProcessingAction.getType()) {
			case PROCESSING:

				// Set the input and output semi-product
				inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
				entity.setInputSemiProduct(inputSemiProduct);

				outputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getOutputSemiProduct().getId());
				entity.setOutputSemiProduct(outputSemiProduct);

				break;

			case FINAL_PROCESSING:

				// Set the input semi-product
				inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
				entity.setInputSemiProduct(inputSemiProduct);

				// Set the output final product
				outputFinalProduct = finalProductService.fetchFinalProduct(apiProcessingAction.getOutputFinalProduct().getId());
				entity.setOutputFinalProduct(outputFinalProduct);

				break;

			case TRANSFER:
			case SHIPMENT:

				if (BooleanUtils.isTrue(apiProcessingAction.getFinalProductAction())) {

					// Set the input final product and set the output to be the same as the input
					inputFinalProduct = finalProductService.fetchFinalProduct(apiProcessingAction.getInputFinalProduct().getId());
					entity.setInputFinalProduct(inputFinalProduct);
					entity.setOutputFinalProduct(inputFinalProduct);
				} else {

					// Set the input semi-product and set the output to be the same as the input
					inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
					entity.setInputSemiProduct(inputSemiProduct);
					entity.setOutputSemiProduct(inputSemiProduct);
				}
				break;

			case GENERATE_QR_CODE:

				// Set the input and output semi-product (the output is the same with the input)
				inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
				entity.setInputSemiProduct(inputSemiProduct);
				entity.setOutputSemiProduct(inputSemiProduct);
		}
	}

	private void updateRequiredEvidenceFields(ApiProcessingAction apiProcessingAction, ProcessingAction entity) {

		// Delete requiredEvidenceFields that are not present in the request
		entity.getProcessingEvidenceFields().removeAll(
				entity.getProcessingEvidenceFields()
						.stream()
						.filter(field -> apiProcessingAction.getRequiredEvidenceFields()
								.stream()
								.noneMatch(ref -> ref.getId().equals(field.getProcessingEvidenceField().getId())))
						.collect(Collectors.toList())
		);

		// Update or add requiredEvidenceFields
		apiProcessingAction.getRequiredEvidenceFields().forEach(

				requiredEvidenceField -> {

					ProcessingActionPEF processingActionPEF = entity.getProcessingEvidenceFields()
							.stream()
							.filter(p -> p.getProcessingEvidenceField().getId().equals(requiredEvidenceField.getId()))
							.findFirst()
							.orElse(new ProcessingActionPEF());

					try {
						processingActionPEF.setProcessingEvidenceField(
								processingEvidenceFieldService.fetchProcessingEvidenceField(requiredEvidenceField.getId()));
						processingActionPEF.setMandatory(
								requiredEvidenceField.getMandatory() != null && requiredEvidenceField.getMandatory());
						processingActionPEF.setRequiredOnQuote(
								requiredEvidenceField.getRequiredOnQuote() != null && requiredEvidenceField.getRequiredOnQuote());
						processingActionPEF.setProcessingAction(entity);
					} catch (ApiException e) {
						e.printStackTrace();
					}
					entity.getProcessingEvidenceFields().add(processingActionPEF);
				}
		);
	}

	private void updateRequiredEvidenceTypes(ApiProcessingAction apiProcessingAction, ProcessingAction entity) {

		// Delete requiredDocumentTypes that are not present in the request
		entity.getRequiredDocumentTypes().removeAll(
				entity.getRequiredDocumentTypes()
						.stream()
						.filter(p -> apiProcessingAction.getRequiredDocumentTypes()
								.stream()
								.noneMatch(rdt -> rdt.getId().equals(p.getProcessingEvidenceType().getId())))
						.collect(Collectors.toList())
		);

		// Update or add requiredDocumentTypes
		apiProcessingAction.getRequiredDocumentTypes().forEach(

				requiredDocumentType -> {

					ProcessingActionPET processingActionProcessingEvidenceType = entity.getRequiredDocumentTypes()
							.stream()
							.filter(p -> p.getProcessingEvidenceType().getId().equals(requiredDocumentType.getId()))
							.findFirst()
							.orElse(new ProcessingActionPET());

					try {
						processingActionProcessingEvidenceType.setProcessingEvidenceType(
								processingEvidenceTypeService.fetchProcessingEvidenceType(requiredDocumentType.getId()));
						processingActionProcessingEvidenceType.setMandatory(
								requiredDocumentType.getMandatory() != null && requiredDocumentType.getMandatory());
						processingActionProcessingEvidenceType.setRequiredOnQuote(
								requiredDocumentType.getRequiredOnQuote() != null && requiredDocumentType.getRequiredOnQuote());
						processingActionProcessingEvidenceType.setRequiredOneOfGroupIdForQuote(requiredDocumentType.getRequiredOneOfGroupIdForQuote());
						processingActionProcessingEvidenceType.setProcessingAction(entity);
					} catch (ApiException e) {
						e.printStackTrace();
					}
					entity.getRequiredDocumentTypes().add(processingActionProcessingEvidenceType);
				}
		);
	}

	private void updateSupportedFacilities(ApiProcessingAction apiProcessingAction, ProcessingAction entity) throws ApiException {

		// Delete facilities that are not present
		entity.getProcessingActionFacilities().removeAll(
				entity.getProcessingActionFacilities()
						.stream()
						.filter(paf -> apiProcessingAction.getSupportedFacilities()
								.stream()
								.noneMatch(f -> f.getId().equals(paf.getFacility().getId())))
						.collect(Collectors.toList())
		);

		// Create newly added supported facilities
		for (ApiFacility supportedFacility : apiProcessingAction.getSupportedFacilities()) {
			ProcessingActionFacility processingActionFacility = entity.getProcessingActionFacilities()
					.stream()
					.filter(paf -> supportedFacility.getId().equals(paf.getFacility().getId()))
					.findAny()
					.orElse(new ProcessingActionFacility());

			if (processingActionFacility.getId() == null) {

				processingActionFacility.setFacility(facilityService.fetchFacility(supportedFacility.getId()));
				processingActionFacility.setProcessingAction(entity);

				entity.getProcessingActionFacilities().add(processingActionFacility);
			}
		}
	}

	private void updateProcActionTranslations(ApiProcessingAction apiProcessingAction, ProcessingAction entity) {

		// Remove translations that are not part of the request
		entity.getProcessingActionTranslations().removeAll(
				entity.getProcessingActionTranslations()
						.stream()
						.filter(translation -> apiProcessingAction.getTranslations()
								.stream()
								.noneMatch(t -> t.getLanguage().equals(translation.getLanguage())))
						.collect(Collectors.toList())
		);

		// Update or add translations
		apiProcessingAction.getTranslations().forEach(
				translation -> {
					ProcessingActionTranslation pat = entity.getProcessingActionTranslations()
							.stream()
							.filter(t -> t.getLanguage().equals(translation.getLanguage()))
							.findFirst()
							.orElse(new ProcessingActionTranslation());
					pat.setName(translation.getName());
					pat.setDescription(translation.getDescription());
					pat.setLanguage(translation.getLanguage());
					pat.setProcessingAction(entity);
					entity.getProcessingActionTranslations().add(pat);
				}
		);
	}
}
