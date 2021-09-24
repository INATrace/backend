package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.common.mappers.DocumentMapper;
import com.abelium.inatrace.components.company.api.ApiCompanyDocument;
import com.abelium.inatrace.db.entities.company.CompanyDocument;

public class CompanyDocumentMapper {

    public static ApiCompanyDocument toApiCompanyDocument(CompanyDocument entity){
        ApiCompanyDocument apiCompanyDocument = new ApiCompanyDocument();
        apiCompanyDocument.setDocument(DocumentMapper.toApiDocument(entity.getDocument()));
        apiCompanyDocument.setName(entity.getName());
        apiCompanyDocument.setDescription(entity.getDescription());
        apiCompanyDocument.setCategory(entity.getCategory());
        apiCompanyDocument.setType(entity.getType());
        apiCompanyDocument.setLink(entity.getLink());
        apiCompanyDocument.setQuote(entity.getQuote());
        return apiCompanyDocument;

    }

}
