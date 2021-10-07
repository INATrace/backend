package com.abelium.inatrace.components.currencies.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;

public class ApiCurrencyTypeRequest extends ApiPaginatedRequest {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
