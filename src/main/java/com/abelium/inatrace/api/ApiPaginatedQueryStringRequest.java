package com.abelium.inatrace.api;

import io.swagger.v3.oas.annotations.Parameter;

public class ApiPaginatedQueryStringRequest extends ApiPaginatedRequest {
	
    @Parameter(name = "Generic query string for (a part of) name, email etc.")
    public String queryString;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
