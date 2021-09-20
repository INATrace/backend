package com.abelium.inatrace.components.processingaction;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionPET;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionTranslation;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;

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
				.map(ProcessingActionMapper::toApiProcessingAction)
				.collect(Collectors.toList()), count);
	}


	public ApiProcessingAction getProcessingAction(Long id, Language language) throws ApiException {
		
		ProcessingAction processingAction = fetchProcessingAction(id);
		ProcessingActionTranslation translation = 
				processingAction.getProcessingActionTranslations()
					.stream()
					.filter(l -> language.equals(l.getLanguage()))
					.findAny()
					.orElse(null);
			processingAction.getProcessingActionTranslations().clear();
			processingAction.getProcessingActionTranslations().add(translation);
		return ProcessingActionMapper.toApiProcessingAction(processingAction);
	}

	@Transactional
	public ApiBaseEntity createOrUpdateProcessingAction(ApiProcessingAction apiProcessingAction) throws ApiException {

		ProcessingAction entity;
		ProcessingActionTranslation translation = null;

		if (apiProcessingAction.getId() != null) {
			entity = fetchProcessingAction(apiProcessingAction.getId());
			if (apiProcessingAction.getLanguage() == null) apiProcessingAction.setLanguage(Language.EN); 
			translation = 
				entity.getProcessingActionTranslations()
					.stream()
					.filter(l -> apiProcessingAction.getLanguage().equals(l.getLanguage()))
					.findAny()
					.orElse(null);
		} else {
			entity = new ProcessingAction();
		}
		
		if (translation != null) {
			translation.setLanguage(apiProcessingAction.getLanguage());
		} else {
			translation = new ProcessingActionTranslation();
			translation.setLanguage(apiProcessingAction.getLanguage() == null ? Language.EN : apiProcessingAction.getLanguage());
		}
		
		translation.setProcessingAction(entity);
		translation.setName(apiProcessingAction.getName());
		translation.setDescription(apiProcessingAction.getDescription());
		translation.setLanguage(apiProcessingAction.getLanguage() == null ? Language.EN : apiProcessingAction.getLanguage());

		entity.getProcessingActionTranslations().add(translation);
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
		
		SemiProduct inputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getInputSemiProduct().getId());
		if (inputSemiProduct != null) {
			entity.setInputSemiProduct(inputSemiProduct);
		}

		if (apiProcessingAction.getOutputSemiProduct() != null && apiProcessingAction.getOutputSemiProduct().getId() != null) {
			SemiProduct outputSemiProduct = semiProductService.fetchSemiProduct(apiProcessingAction.getOutputSemiProduct().getId());
			if (outputSemiProduct != null) {
				entity.setOutputSemiProduct(outputSemiProduct);
			}
		}

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
				.map(ProcessingActionMapper::toApiProcessingAction)
				.collect(Collectors.toList()), count);
	}
}
