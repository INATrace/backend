package com.abelium.INATrace.components.documents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.types.MediaObject;

@Lazy
@Service
public class DocumentEngine {
	
    protected final Logger logger = LoggerFactory.getLogger(DocumentEngine.class);
    
    @Autowired
    DocumentGeneratorEngine documentGeneratorEngine;
    
    
    public MediaObject convertDocxToPdf(byte[] docx) throws ApiException {
        try {
            return new MediaObject(MediaType.APPLICATION_OCTET_STREAM, documentGeneratorEngine.convertDocxToPdf(docx));
        } catch (Exception e) {
        	logger.error("Docx to pdf conversion error", e);
            throw new ApiException(ApiStatus.ERROR, "Error converting docx to pdf");
        }
    }    
    
}
