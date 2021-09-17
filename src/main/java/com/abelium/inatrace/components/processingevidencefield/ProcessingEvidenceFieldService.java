package com.abelium.inatrace.components.processingevidencefield;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.processingevidencefield.api.ApiFileInfo;
import com.abelium.inatrace.components.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.db.entities.processingevidencefield.FileInfo;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;

/**
 * Service for processing evidence field entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class ProcessingEvidenceFieldService extends BaseService {

	public ApiPaginatedList<ApiProcessingEvidenceField> getProcessingEvidenceFieldList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> processingEvidenceFieldQueryObject(request), ProcessingEvidenceFieldMapper::toApiProcessingEvidenceField);
	}

	private ProcessingEvidenceField processingEvidenceFieldQueryObject(ApiPaginatedRequest request) {

		ProcessingEvidenceField processingEvidenceFieldProxy = Torpedo.from(ProcessingEvidenceField.class);
		if ("label".equals(request.sortBy)) {
			QueryTools.orderBy(request.sort, processingEvidenceFieldProxy.getLabel());
		} else {
			QueryTools.orderBy(request.sort, processingEvidenceFieldProxy.getId());
		}

		return processingEvidenceFieldProxy;
	}

	public ApiProcessingEvidenceField getProcessingEvidenceField(Long id) throws ApiException {
		return ProcessingEvidenceFieldMapper.toApiProcessingEvidenceField(fetchProcessingEvidenceField(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateProcessingEvidenceField(ApiProcessingEvidenceField apiProcessingEvidenceField) throws ApiException {

		ProcessingEvidenceField entity;

		if (apiProcessingEvidenceField.getId() != null) {
			entity = fetchProcessingEvidenceField(apiProcessingEvidenceField.getId());
		} else {
			entity = new ProcessingEvidenceField();
		}

		// ApiProcessingEvidenceField object
		entity.setLabel(apiProcessingEvidenceField.getLabel());
		entity.setNumericValue(apiProcessingEvidenceField.getNumericValue());
		entity.setStringValue(apiProcessingEvidenceField.getStringValue());
		entity.setFileMultiplicity(apiProcessingEvidenceField.getFileMultiplicity());
		entity.setType(apiProcessingEvidenceField.getType());
		
		List<FileInfo> filesInfos = new ArrayList<>();

		// Get list of files infos
		List<ApiFileInfo> apiFilesInfos = apiProcessingEvidenceField.getFiles();
		if (apiFilesInfos != null && !apiFilesInfos.isEmpty()) {
			apiFilesInfos.forEach(
					apiFileInfo -> {
						
						FileInfo fileInfo = new FileInfo();
						fileInfo.setStorageKey(apiFileInfo.getStorageKey());
						fileInfo.setName(apiFileInfo.getName());
						fileInfo.setContentType(apiFileInfo.getContentType());
						fileInfo.setSize(apiFileInfo.getSize());
						
						filesInfos.add(fileInfo);
					}
			);
		}
		
		entity.setFiles(filesInfos);

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteProcessingEvidenceField(Long id) throws ApiException {

		ProcessingEvidenceField processingEvidenceField = fetchProcessingEvidenceField(id);
		em.remove(processingEvidenceField);
	}

	public ProcessingEvidenceField fetchProcessingEvidenceField(Long id) throws ApiException {

		ProcessingEvidenceField processingEvidenceField = Queries.get(em, ProcessingEvidenceField.class, id);
		if (processingEvidenceField == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid processing evidence field ID");
		}
		return processingEvidenceField;
	}
	
	public ApiPaginatedList<ApiProcessingEvidenceField> listProcessingEvidenceFieldsByValueChain(Long valueChainId, ApiPaginatedRequest request) {

		TypedQuery<ProcessingEvidenceField> processingEvidenceFieldsQuery = em.createNamedQuery("ProcessingEvidenceField.listProcessingEvidenceFieldsByValueChain", ProcessingEvidenceField.class)
			.setParameter("valueChainId", valueChainId)
			.setFirstResult(request.getOffset())
			.setMaxResults(request.getLimit());

		List<ProcessingEvidenceField> processingEvidenceFields = processingEvidenceFieldsQuery.getResultList();

		Long count = em.createNamedQuery("ProcessingEvidenceField.countProcessingEvidenceFieldsByValueChain", Long.class)
			.setParameter("valueChainId", valueChainId)
			.getSingleResult();

		return new ApiPaginatedList<>(
			processingEvidenceFields
				.stream()
				.map(ProcessingEvidenceFieldMapper::toApiProcessingEvidenceField)
				.collect(Collectors.toList()), count);
	}
	
}
