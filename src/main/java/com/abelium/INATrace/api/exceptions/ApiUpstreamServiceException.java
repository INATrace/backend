package com.abelium.INATrace.api.exceptions;

import org.springframework.http.HttpStatus;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.api.errors.validation.ApiValidationErrorDetails;

/**
 * Common class for service client exceptions. Actual exception thrown will always be one of 
 * {@link ApiUpstreamApplicationException} for error responses returned by upstream services and
 * {@link ApiUpstreamNetworkException} for low-level problems on network or due to service outage.  
 */
@SuppressWarnings("serial")
public abstract class ApiUpstreamServiceException extends ApiException
{
    public ApiUpstreamServiceException(ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(apiStatus, errorMessage, validationErrorDetails, errorDetails);
    }

    public ApiUpstreamServiceException(HttpStatus httpStatus, ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(httpStatus, apiStatus, errorMessage, validationErrorDetails, errorDetails);
    }
}
