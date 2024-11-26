package com.abelium.inatrace.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public class ApiPaginatedQueryStringRequest extends ApiPaginatedRequest {
	
    @Parameter(description = "Generic query string for (a part of) name, email etc.")
    public String queryString;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
