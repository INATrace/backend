package com.abelium.inatrace.api.exceptions;

import org.springframework.http.HttpStatus;

import com.abelium.inatrace.api.ApiStatus;

/**
 * Exception thrown when upstream service is down or there is some network problem.
 */
@SuppressWarnings("serial")
public class ApiUpstreamNetworkException extends ApiUpstreamServiceException
{
    public ApiUpstreamNetworkException(String errorMessage) {
        super(ApiStatus.UPSTREAM_HTTP_ERROR, errorMessage, null, null);
    }

    public ApiUpstreamNetworkException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, ApiStatus.UPSTREAM_HTTP_ERROR, errorMessage, null, null);
    }

    @Override
    public HttpStatus responseHttpStatus() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
