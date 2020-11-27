package com.abelium.INATrace.components.common;

import java.util.EnumSet;

import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiPaginatedList;
import com.abelium.INATrace.api.ApiPaginatedQueryStringRequest;
import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.api.ApiCountry;
import com.abelium.INATrace.components.common.api.ApiDocument;
import com.abelium.INATrace.db.entities.Country;
import com.abelium.INATrace.db.entities.Document;
import com.abelium.INATrace.tools.ImageTools;
import com.abelium.INATrace.tools.PaginationTools;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.tools.QueryTools;
import com.abelium.INATrace.tools.ResourceTools;
import com.abelium.INATrace.types.DocumentType;
import com.abelium.INATrace.types.MediaObject;

@Lazy
@Service
public class CommonEngine extends BaseEngine {
	
	@Autowired
	private StorageEngine storageEngine;

    private Country countryListQueryObject(ApiPaginatedQueryStringRequest request) {
        Country cProxy = Torpedo.from(Country.class);

        if (StringUtils.isNotBlank(request.queryString)) {
            Torpedo.where(cProxy.getName()).like().startsWith(request.queryString);
        }
        switch (request.sortBy) {
        	case "name": QueryTools.orderBy(request.sort, cProxy.getName()); break;
        	default: QueryTools.orderBy(request.sort, cProxy.getId());
        }
        return cProxy;
    }	
	
    @Transactional
    public ApiPaginatedList<ApiCountry> fetchCountryList(ApiPaginatedQueryStringRequest filterRequest) {
        return PaginationTools.createPaginatedResponse(em, filterRequest, () -> countryListQueryObject(filterRequest),
        		CommonApiTools::toApiCountry);
    }

    public ApiDocument uploadDocument(Long userId, byte[] file, String name, String contentType, Long size, DocumentType type) throws ApiException {
        if (file == null) {
            throw new ApiException(ApiStatus.REQUEST_BODY_ERROR, "Uploading file failed. File is null.");
        }
        Document document = storageEngine.uploadDocument(file, name, contentType, size, type);
        return CommonApiTools.toApiDocument(document, userId);
    }
    
	public ApiDocument uploadImage(Long userId, byte[] file, String name, String contentType, long size, boolean resize) throws ApiException {
        if (file == null) {
            throw new ApiException(ApiStatus.REQUEST_BODY_ERROR, "Uploading image failed. File is null.");
        }
        
        // First filter; we'll check type later with Tika library
        if (!MediaType.IMAGE_JPEG_VALUE.equals(contentType) && !MediaType.IMAGE_PNG_VALUE.equals(contentType)) {
        	throw new ApiException(ApiStatus.INVALID_REQUEST, "Unsupported file type.");
        }
        
        String fileType = ResourceTools.mediaTypeDetector().detect(file);
        
        if (!MediaType.IMAGE_JPEG_VALUE.equals(fileType) && !MediaType.IMAGE_PNG_VALUE.equals(fileType)) {
        	throw new ApiException(ApiStatus.INVALID_REQUEST, "Unsupported file type.");
        }
        
        Document document;
        
        if (resize) {
        	document = storageEngine.uploadImageVariants(file, ImageTools.STANDARD_IMAGE_SIZES, fileType, name);
        } else {
            document = storageEngine.uploadDocument(file, name, fileType, size, DocumentType.IMAGE);
        }
        return CommonApiTools.toApiDocument(document, userId);        
	}

    public MediaObject getDocument(Long userId, String tempKey, EnumSet<DocumentType> allowedTypes) throws ApiException {
    	String storageKey = StorageKeyCache.get(tempKey, userId);
        DocumentData document = storageEngine.downloadDocument(storageKey, allowedTypes);
        MediaType mediaType = document.data.getContentType() == null ? MediaType.APPLICATION_OCTET_STREAM : MediaType.valueOf(document.data.getContentType());
        return new MediaObject(mediaType, document.file);
    }
    
	public MediaObject getImage(Long userId, String tempKey, String size) throws ApiException {
    	String storageKey = StorageKeyCache.get(tempKey, userId);
        DocumentData document = storageEngine.downloadImage(storageKey, size);
        MediaType mediaType = document.data.getContentType() == null ? MediaType.APPLICATION_OCTET_STREAM : MediaType.valueOf(document.data.getContentType());
        return new MediaObject(mediaType, document.file);
	}
    
    @Transactional 
    public Document fetchDocument(Long userId, ApiDocument ad) {
    	if (ad == null) {
    		return null;
    	} else {
        	String storageKey = StorageKeyCache.get(ad.storageKey, userId);
    		return Queries.getUniqueBy(em, Document.class, Document::getStorageKey, storageKey);
    		// return Queries.get(em, Document.class, ad.id);
    	}
    	
    }
    
    @Transactional
    public Country fetchCountry(ApiCountry ac) throws ApiException {
    	if (ac == null) return null;
    	
    	Country c = Queries.get(em, Country.class, ac.id);
    	if (c == null) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid country id");
    	}
    	return c;
    }



}
