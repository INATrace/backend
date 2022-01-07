package com.abelium.inatrace.api.errors;

import org.springframework.http.HttpStatus;

import com.abelium.inatrace.api.ApiStatus;

@SuppressWarnings("serial")
public class ApiImageException extends ApiException {

    public ApiImageException(HttpStatus httpStatus) {
        super(httpStatus, ApiStatus.ERROR, "");
    }
}
