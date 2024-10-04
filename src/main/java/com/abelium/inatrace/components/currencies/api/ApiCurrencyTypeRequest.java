package com.abelium.inatrace.components.currencies.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public class ApiCurrencyTypeRequest extends ApiPaginatedRequest {

    @Parameter
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
