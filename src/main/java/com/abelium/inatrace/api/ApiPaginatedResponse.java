package com.abelium.inatrace.api;


import java.util.List;

public class ApiPaginatedResponse<DATATYPE> extends ApiResponse<ApiPaginatedList<DATATYPE>> 
{
    protected ApiPaginatedResponse() {
    }
    
    public ApiPaginatedResponse(List<DATATYPE> items, int count) {
        super(new ApiPaginatedList<>(items, count));
    }
    
    public ApiPaginatedResponse(ApiPaginatedList<DATATYPE> paginatedList) {
        super(paginatedList);
    }
    
}
