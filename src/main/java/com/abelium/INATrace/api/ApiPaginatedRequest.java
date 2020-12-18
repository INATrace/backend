package com.abelium.INATrace.api;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.types.PaginatedRequestType;
import com.abelium.INATrace.types.SortDirection;

import io.swagger.annotations.ApiParam;

@Validated
public class ApiPaginatedRequest {

	@ApiParam(value = "Only count, only fetch, or return both values (if null)") 
	public PaginatedRequestType requestType = null;
	
    @Min(1)
    @ApiParam(value = "Number of records to return. Min: 1, default: 100", defaultValue = "100")
    public int limit = 100;

    @Min(0)
    @ApiParam(value = "Number of records to skip before returning. Default: 0, min: 0", defaultValue = "0")
    public int offset = 0;

    @ApiParam(value = "Column name to be sorted by, varies for each endpoint, default is id")
    public String sortBy = "id";

    @ApiParam(value = "Direction of sorting (ASC or DESC). Default DESC.", defaultValue = "DESC")
    public SortDirection sort = SortDirection.DESC;

    
    public PaginatedRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(PaginatedRequestType requestType) {
		this.requestType = requestType;
	}

	public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public SortDirection getSort() {
        return sort;
    }

    public void setSort(SortDirection sort) {
        this.sort = sort;
    }
}
	
