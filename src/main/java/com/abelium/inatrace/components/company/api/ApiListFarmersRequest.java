package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiListFarmersRequest extends ApiPaginatedRequest {

    @ApiParam(value = "Name or surname")
    private String query;

    @ApiParam(value = "Search by parameter")
    private String searchBy;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }
}
