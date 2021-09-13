package com.abelium.inatrace.components.processingevidencefield;

import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.components.processingevidencefield.api.ApiFileInfo;
import com.abelium.inatrace.components.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.db.entities.processingevidencefield.FileInfo;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;

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
		apiProcessingEvidenceField.setLabel(entity.getLabel());
		apiProcessingEvidenceField.setRequired(entity.getRequired());
		apiProcessingEvidenceField.setMandatory(entity.getMandatory());
		apiProcessingEvidenceField.setRequiredOnQuote(entity.getRequiredOnQuote());
		apiProcessingEvidenceField.setNumericValue(entity.getNumericValue());
		apiProcessingEvidenceField.setStringValue(entity.getStringValue());
//		apiProcessingEvidenceField.setObjectValue(entity.getObjectValue());
		apiProcessingEvidenceField.setFileMultiplicity(entity.getFileMultiplicity());
		apiProcessingEvidenceField.setType(entity.getType());
		
		List<ApiFileInfo> apiFileInfos = new ArrayList<>();

		// Get list of files' infos
		List<FileInfo> processingEFFiles = entity.getFiles();
		processingEFFiles.forEach(
			fileInfo -> {
				
				ApiFileInfo apiFileInfo = new ApiFileInfo();
				apiFileInfo.setId(fileInfo.getId());
				apiFileInfo.setStorageKey(fileInfo.getStorageKey());
				apiFileInfo.setName(fileInfo.getName());
				apiFileInfo.setContentType(fileInfo.getContentType());
				apiFileInfo.setSize(fileInfo.getSize());
			}
		);

		apiProcessingEvidenceField.setFiles(apiFileInfos);

		return apiProcessingEvidenceField;
	}
}
