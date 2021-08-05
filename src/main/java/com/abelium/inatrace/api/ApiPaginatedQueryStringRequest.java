package com.abelium.inatrace.api;

import io.swagger.annotations.ApiParam;

public class ApiPaginatedQueryStringRequest extends ApiPaginatedRequest {
	
    @ApiParam(value = "Generic query string for (a part of) name, email etc.")
    public String queryString;


    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
