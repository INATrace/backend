package com.abelium.inatrace.api.errors.validation;

import java.util.Map;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiError;

public class ApiValidationError extends ApiError
{
    public static final String DEFAULT_MESSAGE = "Invalid field(s) in request";

    public ApiValidationError() {
        super(ApiStatus.VALIDATION_ERROR, DEFAULT_MESSAGE, new ApiValidationErrorDetails(), null);
    }

    public ApiValidationError(String errorMessage, String className, Map<String, String> fieldErrors) {
        super(ApiStatus.VALIDATION_ERROR, errorMessage, new ApiValidationErrorDetails(className, fieldErrors), null);
    }
}
