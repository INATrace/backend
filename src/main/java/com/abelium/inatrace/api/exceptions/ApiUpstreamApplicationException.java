package com.abelium.inatrace.api.exceptions;

import org.springframework.http.HttpStatus;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.validation.ApiValidationErrorDetails;

/**
 * Exception thrown when upstream service returns error status.
 */
@SuppressWarnings("serial")
public class ApiUpstreamApplicationException extends ApiUpstreamServiceException
{
    public ApiUpstreamApplicationException(ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(apiStatus, errorMessage, validationErrorDetails, errorDetails);
    }

    public ApiUpstreamApplicationException(HttpStatus httpStatus, ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(httpStatus, apiStatus, errorMessage, validationErrorDetails, errorDetails);
    }
}
