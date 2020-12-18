package com.abelium.INATrace.components.common;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abelium.INATrace.api.ApiDefaultResponse;
import com.abelium.INATrace.api.ApiPaginatedQueryStringRequest;
import com.abelium.INATrace.api.ApiPaginatedResponse;
import com.abelium.INATrace.api.ApiResponse;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.api.ApiCountry;
import com.abelium.INATrace.components.common.api.ApiDocument;
import com.abelium.INATrace.components.common.api.ApiGlobalSettingsValue;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.types.DocumentType;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/common")
public class CommonController 
{
    @Autowired
    private CommonEngine commonEngine;
    
    @Autowired
    private GlobalSettingsEngine globalSettingsEngine;
    
    
    @ApiOperation(value = "List countries")
    @GetMapping(value = "/countries")
    public ApiPaginatedResponse<ApiCountry> getCountries(@Valid ApiPaginatedQueryStringRequest filterRequest) {
        return new ApiPaginatedResponse<>(commonEngine.fetchCountryList(filterRequest));
    }
    
    @ApiOperation(value = "Uploads a document")
    @PostMapping(value = "/document", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResponse<ApiDocument> uploadDocument(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestParam(value = "type", defaultValue = "GENERAL") DocumentType type,
            @RequestParam("file") MultipartFile file) throws ApiException, IOException {
        return new ApiResponse<>(commonEngine.uploadDocument(authUser.getUserId(), file.getBytes(), file.getOriginalFilename(), file.getContentType(), file.getSize(), type));
    }    

    @ApiOperation(value = "Uploads an image")
    @PostMapping(value = "/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResponse<ApiDocument> uploadImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@RequestParam(value = "resize", defaultValue = "false") boolean resize,
    		@RequestParam("file") MultipartFile file) throws ApiException, IOException {
        return new ApiResponse<>(commonEngine.uploadImage(authUser.getUserId(), file.getBytes(), file.getOriginalFilename(), file.getContentType(), file.getSize(), resize));
    }    
    
    @ApiOperation(value = "Returns file contents for given storage key")
    @GetMapping(value = "/document/{storageKey}")
    public ResponseEntity<byte[]> getDocument(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getDocument(authUser.getUserId(), storageKey, null).toResponseEntity();
    }
    
    @ApiOperation(value = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}")
    public ResponseEntity<byte[]> getImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getImage(authUser.getUserId(), storageKey, null).toResponseEntity();
    }

    @ApiOperation(value = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}/{size}")
    public ResponseEntity<byte[]> getResizedImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey,
    		@Valid @PathVariable(value = "size", required = true) String size) throws ApiException {
        return commonEngine.getImage(authUser.getUserId(), storageKey, size).toResponseEntity();
    }
    
    @ApiOperation(value = "Returns 'global settings' value")
    @GetMapping(value = "/globalSettings/{name}")
    public ApiResponse<ApiGlobalSettingsValue> getGlobalSettings(@Valid @PathVariable(value = "name", required = true) String name) {
        return new ApiResponse<>(new ApiGlobalSettingsValue(globalSettingsEngine.getSettings(name, false)));
    }    

    @ApiOperation(value = "Updates or creates 'global settings'")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/globalSettings/{name}")
    public ApiDefaultResponse updateGlobalSettings(
    		@Valid @PathVariable(value = "name", required = true) String name,
    		@Valid @RequestBody ApiGlobalSettingsValue request) {
    	globalSettingsEngine.updateSettings(name, request.value, request.isPublic);
        return new ApiDefaultResponse();
    }    
    
}
