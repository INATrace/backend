package com.abelium.inatrace.components.common.mappers;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.db.entities.common.Document;

public class DocumentMapper {

    public static ApiDocument toApiDocument(Document entity){
        ApiDocument apiDocument = new ApiDocument();
        apiDocument.setId(entity.getId());
        apiDocument.setName(entity.getName());
        apiDocument.setSize(entity.getSize());
        apiDocument.setContentType(entity.getContentType());
        apiDocument.setStorageKey(entity.getStorageKey());
        return apiDocument;
    }
}
