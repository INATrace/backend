package com.abelium.inatrace.tools;

import com.abelium.inatrace.api.errors.ApiException;

@FunctionalInterface
public interface ThrowableFunction<T, R> {
	R apply(T t) throws ApiException;	   
}
