package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedQueryStringRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.api.ApiCountry;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.common.api.ApiGlobalSettingsValue;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.DocumentType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonEngine;
    
    @Autowired
    private GlobalSettingsService globalSettingsEngine;
    
    @Operation(summary = "List countries")
    @GetMapping(value = "/countries")
    public ApiPaginatedResponse<ApiCountry> getCountries(@Valid ApiPaginatedQueryStringRequest filterRequest) {
        return new ApiPaginatedResponse<>(commonEngine.fetchCountryList(filterRequest));
    }
    
    @Operation(summary = "Uploads a document")
    @PostMapping(value = "/document", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResponse<ApiDocument> uploadDocument(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestParam(value = "type", defaultValue = "GENERAL") DocumentType type,
            @RequestParam("file") MultipartFile file) throws ApiException, IOException {
        return new ApiResponse<>(commonEngine.uploadDocument(authUser.getUserId(), file.getBytes(), file.getOriginalFilename(), file.getContentType(), file.getSize(), type));
    }    

    @Operation(summary = "Uploads an image")
    @PostMapping(value = "/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResponse<ApiDocument> uploadImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@RequestParam(value = "resize", defaultValue = "true") boolean resize,
    		@RequestParam("file") MultipartFile file) throws ApiException, IOException {
        return new ApiResponse<>(commonEngine.uploadImage(authUser.getUserId(), file.getBytes(), file.getOriginalFilename(), file.getContentType(), file.getSize(), resize));
    }    
    
    @Operation(summary = "Returns file contents for given storage key")
    @GetMapping(value = "/document/{storageKey}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    public ResponseEntity<byte[]> getDocument(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getDocument(authUser.getUserId(), storageKey, null).toResponseEntity();
    }
    
    @Operation(summary = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    public ResponseEntity<byte[]> getImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getImage(authUser.getUserId(), storageKey, null).toResponseEntity();
    }

    @Operation(summary = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}/{size}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    public ResponseEntity<byte[]> getResizedImage(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "storageKey", required = true) String storageKey,
    		@Valid @PathVariable(value = "size", required = true) String size) throws ApiException {
        return commonEngine.getImage(authUser.getUserId(), storageKey, size).toResponseEntity();
    }
    
    @Operation(summary = "Returns 'global settings' value")
    @GetMapping(value = "/globalSettings/{name}")
    public ApiResponse<ApiGlobalSettingsValue> getGlobalSettings(@Valid @PathVariable(value = "name", required = true) String name) {
        return new ApiResponse<>(new ApiGlobalSettingsValue(globalSettingsEngine.getSettings(name, false)));
    }    

    @Operation(summary = "Updates or creates 'global settings'")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PostMapping(value = "/globalSettings/{name}")
    public ApiDefaultResponse updateGlobalSettings(
    		@Valid @PathVariable(value = "name", required = true) String name,
    		@Valid @RequestBody ApiGlobalSettingsValue request) {
    	globalSettingsEngine.updateSettings(name, request.value, request.isPublic);
        return new ApiDefaultResponse();
    }    
    
}
