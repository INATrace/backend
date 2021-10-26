package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processing_evidence_type.ProcessingEvidenceTypeService;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.company.CompanyQueries;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.processingevidencefield.ProcessingEvidenceFieldService;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPEF;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPET;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for processing action entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class ProcessingActionService extends BaseService {

	@Autowired
	private CompanyQueries companyQueries;

	@Autowired
	private SemiProductService semiProductService;

	@Autowired
	private ProcessingEvidenceTypeService processingEvidenceTypeService;
	
	@Autowired
	private ProcessingEvidenceFieldService processingEvidenceFieldService;

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
	public ApiBaseEntity createOrUpdateProcessingAction(ApiProcessingAction apiProcessingAction) throws ApiException {

		ProcessingAction entity = apiProcessingAction.getId() != null
				? fetchProcessingAction(apiProcessingAction.getId())
				: new ProcessingAction();

		// Validate input semi-product - should be present in every case
		if (apiProcessingAction.getInputSemiProduct() == null || apiProcessingAction.getInputSemiProduct().getId() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Input semi-product is required");
		}

		// Validate output semi-product - mandatory if the action type is PROCESSING (in the other cases get set automatically to be the same as the input)
		if (ProcessingActionType.PROCESSING == apiProcessingAction.getType() &&
				(apiProcessingAction.getOutputSemiProduct() == null ||
						apiProcessingAction.getOutputSemiProduct().getId() == null)) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Output semi-product is required");
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

		entity.setPrefix(apiProcessingAction.getPrefix());
		entity.setRepackedOutputs(apiProcessingAction.getRepackedOutputs());
		entity.setMaxOutputWeight(apiProcessingAction.getMaxOutputWeight());
		entity.setPublicTimelineLabel(apiProcessingAction.getPublicTimelineLabel());
		entity.setPublicTimelineLocation(apiProcessingAction.getPublicTimelineLocation());
		entity.setPublicTimelineIconType(apiProcessingAction.getPublicTimelineIconType());
		entity.setType(apiProcessingAction.getType());
		
		Company company = companyQueries.fetchCompany(apiProcessingAction.getCompany().getId());
		if (company != null) {
			entity.setCompany(company);
		}

		// Set the input semi-product
		SemiProduct inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
		entity.setInputSemiProduct(inputSemiProduct);

		// Set the output semi-product (if type si other than PROCESSING, set the output semi-product to be the same with the input)
		if (ProcessingActionType.PROCESSING == apiProcessingAction.getType()) {
			SemiProduct outputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getOutputSemiProduct().getId());
			entity.setOutputSemiProduct(outputSemiProduct);
		} else {
			entity.setOutputSemiProduct(inputSemiProduct);
		}
		
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

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteProcessingAction(Long id) throws ApiException {

		ProcessingAction processingAction = fetchProcessingAction(id);
		em.remove(processingAction);
	}

	public ProcessingAction fetchProcessingAction(Long id) throws ApiException {

		ProcessingAction processingAction = Queries.get(em, ProcessingAction.class, id);
		if (processingAction == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid processing action ID");
		}
		return processingAction;
	}
	
	public ApiPaginatedList<ApiProcessingAction> listProcessingActionsByCompany(Long companyId, Language language, ApiPaginatedRequest request) {

		TypedQuery<ProcessingAction> processingActionsQuery = 
			em.createNamedQuery("ProcessingAction.listProcessingActionsByCompany", ProcessingAction.class)
				.setParameter("companyId", companyId)
				.setParameter("language", language)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<ProcessingAction> processingActions = processingActionsQuery.getResultList();

		Long count = em.createNamedQuery("ProcessingAction.countProcessingActionsByCompany", Long.class)
			.setParameter("companyId", companyId)
			.setParameter("language", language)
			.getSingleResult();

		return new ApiPaginatedList<>(
			processingActions
				.stream()
				.map(processingAction -> ProcessingActionMapper.toApiProcessingAction(processingAction, language))
				.collect(Collectors.toList()), count);
	}
}
