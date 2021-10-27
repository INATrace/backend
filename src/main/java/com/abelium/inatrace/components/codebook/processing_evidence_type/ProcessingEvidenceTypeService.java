package com.abelium.inatrace.components.codebook.processing_evidence_type;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for processing evidence type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class ProcessingEvidenceTypeService extends BaseService {

	public ApiPaginatedList<ApiProcessingEvidenceType> getProcEvidenceTypeList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> procEvidenceTypeQueryObject(request),
				ProcessingEvidenceTypeMapper::toApiProcessingEvidenceType);
	}

	private ProcessingEvidenceType procEvidenceTypeQueryObject(ApiPaginatedRequest request) {

		ProcessingEvidenceType processingEvidenceType = Torpedo.from(ProcessingEvidenceType.class);

		switch (request.sortBy) {
			case "code":
				QueryTools.orderBy(request.sort, processingEvidenceType.getCode());
				break;
			case "label":
				QueryTools.orderBy(request.sort, processingEvidenceType.getLabel());
				break;
			default:
				QueryTools.orderBy(request.sort, processingEvidenceType.getId());
		}

		return processingEvidenceType;
	}

	public ApiProcessingEvidenceType getProcessingEvidenceType(Long id) throws ApiException {

		return ProcessingEvidenceTypeMapper.toApiProcessingEvidenceType(fetchProcessingEvidenceType(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateProcessingEvidenceType(ApiProcessingEvidenceType apiProcessingEvidenceType) throws ApiException {

		ProcessingEvidenceType entity;

		if (apiProcessingEvidenceType.getId() != null) {
			entity = fetchProcessingEvidenceType(apiProcessingEvidenceType.getId());
		} else {

			entity = new ProcessingEvidenceType();
			entity.setCode(apiProcessingEvidenceType.getCode());
		}
		entity.setLabel(apiProcessingEvidenceType.getLabel());

		entity.setType(apiProcessingEvidenceType.getType());
		entity.setFairness(apiProcessingEvidenceType.getFairness());
		entity.setProvenance(apiProcessingEvidenceType.getProvenance());
		entity.setQuality(apiProcessingEvidenceType.getQuality());

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteProcessingEvidenceType(Long id) throws ApiException {

		ProcessingEvidenceType processingEvidenceType = fetchProcessingEvidenceType(id);
		em.remove(processingEvidenceType);
	}

	public ProcessingEvidenceType fetchProcessingEvidenceType(Long id) throws ApiException {

		ProcessingEvidenceType processingEvidenceType = Queries.get(em, ProcessingEvidenceType.class, id);
		if (processingEvidenceType == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid processing evidence type ID");
		}

		return processingEvidenceType;
	}

	public ApiPaginatedList<ApiProcessingEvidenceType> listProcessingEvidenceTypesByValueChain(Long valueChainId,
	                                                                                           ApiPaginatedRequest request) {

		TypedQuery<ProcessingEvidenceType> processingEvidenceTypesQuery =
				em.createNamedQuery("ProcessingEvidenceType.listProcessingEvidenceTypesByValueChain", ProcessingEvidenceType.class)
				.setParameter("valueChainId", valueChainId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<ProcessingEvidenceType> processingEvidenceTypes = processingEvidenceTypesQuery.getResultList();

		Long count = em.createNamedQuery("ProcessingEvidenceType.countProcessingEvidenceTypesByValueChain", Long.class)
				.setParameter("valueChainId", valueChainId)
				.getSingleResult();

		return new ApiPaginatedList<>(
				processingEvidenceTypes
						.stream()
						.map(ProcessingEvidenceTypeMapper::toApiProcessingEvidenceTypeBase)
						.collect(Collectors.toList()), count);
	}

}
