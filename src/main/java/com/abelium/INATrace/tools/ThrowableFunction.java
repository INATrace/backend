package com.abelium.INATrace.tools;

import com.abelium.INATrace.api.errors.ApiException;

@FunctionalInterface
public interface ThrowableFunction<T, R> {
	R apply(T t) throws ApiException;	   
}
