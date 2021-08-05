package com.abelium.inatrace.api.errors.validation;

import java.util.Map;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;

@SuppressWarnings("serial")
public class ApiValidationException extends ApiException
{
    public ApiValidationException(String errorMessage, ApiValidationErrorDetails details) {
        super(ApiStatus.VALIDATION_ERROR, errorMessage, details, null);
    }
    
    public ApiValidationException(String errorMessage, String className, Map<String, String> fieldErrors) {
        super(ApiStatus.VALIDATION_ERROR, errorMessage, new ApiValidationErrorDetails(className, fieldErrors), null);
    }
}
