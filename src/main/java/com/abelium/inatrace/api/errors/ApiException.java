package com.abelium.inatrace.api.errors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.validation.ApiValidationErrorDetails;


@SuppressWarnings("serial")
public class ApiException extends Exception
{
    /**
     * Http status of the response.
     */
    private final HttpStatus httpStatus;
    
    /**
     * ApiStatus of the response;
     */
    private final ApiStatus apiStatus;
    
    /**
     * Error details to be included in the response.
     */
    private final String errorDetails;
    
    /**
     * Validation error details to be included in the response.
     */
    private final ApiValidationErrorDetails validationErrorDetails;

    /**
     * Create ApiException with response body as { "status": "...", "errorMessage": "..." }.
     * @param apiStatus {@link ApiStatus} - status to be returned
     * @param errorMessage errorMessage field in response JSON
     */
    public ApiException(ApiStatus apiStatus, String errorMessage) {
        this(apiStatus.getHttpStatus(), apiStatus, errorMessage, null, null);
    }

    /**
     * Create ApiException with custom response body. Allows returning more informative fields then just error message.
     * @param apiStatus {@link ApiStatus} to be returned
     * @param errorMessage errorMessage field in response JSON
     * @param validationErrorDetails validationErrorDetails field in response JSON
     * @param errorDetails errorDetails field in response JSON
     */
    public ApiException(ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        this(apiStatus.getHttpStatus(), apiStatus, errorMessage, validationErrorDetails, errorDetails);
    }
    
    /**
     * Create ApiException with response body as { "status": "...", "errorMessage": "..." }.
     * @param httpStatus http status to be returned, should be something in 4xx or 5xx range
     * @param apiStatus {@link ApiStatus} - status to be returned
     * @param errorMessage errorMessage field in response JSON
     */
    protected ApiException(HttpStatus httpStatus, ApiStatus apiStatus, String errorMessage) {
        this(httpStatus, apiStatus, errorMessage, null, null);
    }

    /**
     * Create ApiException with custom response body. Allows returning more informative fields then just error message.
     * @param httpStatus http status to be returned, should be something in 4xx or 5xx range
     * @param apiStatus {@link ApiStatus} to be returned
     * @param errorMessage errorMessage field in response JSON
     * @param errorDetails errorDetails field in response JSON
     */
    protected ApiException(HttpStatus httpStatus, ApiStatus apiStatus, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(StringUtils.substring(errorMessage, 0, 1024));   // in case we try to return too long error message
        this.httpStatus = httpStatus;
        this.apiStatus = apiStatus;
        this.validationErrorDetails = validationErrorDetails;
        this.errorDetails = errorDetails;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public ApiStatus getApiStatus() {
        return apiStatus;
    }
    
    public ApiValidationErrorDetails getValidationErrorDetails() {
        return validationErrorDetails;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public HttpStatus responseHttpStatus() {
        return httpStatus;
    }
    
    public ApiError createResponseBody() {
        return new ApiError(apiStatus, getMessage(), validationErrorDetails, errorDetails);
    }
}
