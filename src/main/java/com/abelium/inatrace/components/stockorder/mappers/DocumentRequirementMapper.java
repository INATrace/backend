package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.processingevidencefield.ProcessingEvidenceFieldMapper;
import com.abelium.inatrace.components.stockorder.api.ApiDocumentRequirement;
import com.abelium.inatrace.db.entities.stockorder.DocumentRequirement;

import java.util.stream.Collectors;

public class DocumentRequirementMapper {

    public static ApiDocumentRequirement toApiDocumentRequirement(DocumentRequirement entity) {
        if(entity == null) return null;
        ApiDocumentRequirement apiDocumentRequirement = new ApiDocumentRequirement();
        apiDocumentRequirement.setId(entity.getId());
        apiDocumentRequirement.setName(entity.getName());
        apiDocumentRequirement.setDescription(entity.getDescription());
        apiDocumentRequirement.setRequired(entity.getIsRequired());
        apiDocumentRequirement.setScoreTarget(ScoreTargetMapper.toApiScoreTarget(entity.getScoreTarget()));
        apiDocumentRequirement.setScoreImpacts(entity.getScoreImpacts().stream().map(ScoreImpactMapper::toApiScoreImpact).collect(Collectors.toList()));
        apiDocumentRequirement.setFields(entity.getFields().stream().map(ProcessingEvidenceFieldMapper::toApiProcessingEvidenceField).collect(Collectors.toList()));
        return apiDocumentRequirement;
    }
}
