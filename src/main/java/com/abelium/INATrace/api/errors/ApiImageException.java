package com.abelium.INATrace.api.errors;

import org.springframework.http.HttpStatus;

import com.abelium.INATrace.api.ApiStatus;

@SuppressWarnings("serial")
public class ApiImageException extends ApiException {

    public ApiImageException(HttpStatus httpStatus) {
        super(httpStatus, ApiStatus.ERROR, "");
    }
}
