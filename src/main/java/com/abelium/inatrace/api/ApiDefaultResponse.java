package com.abelium.inatrace.api;

import org.springframework.validation.annotation.Validated;

/**
 * ApiDefaultResponse
 */
@Validated
public class ApiDefaultResponse extends ApiResponse<Void> {

    public ApiDefaultResponse() {
        super(null);
    }
    
    public ApiDefaultResponse(ApiStatus status, String errorMessage) {
        super(status, errorMessage);
    }
    
}
