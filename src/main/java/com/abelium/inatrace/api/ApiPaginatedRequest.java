package com.abelium.inatrace.api;

import com.abelium.inatrace.types.PaginatedRequestType;
import com.abelium.inatrace.types.SortDirection;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;

@Validated
@ParameterObject
public class ApiPaginatedRequest {

	@Parameter(description = "Only count, only fetch, or return both values (if null)")
	public PaginatedRequestType requestType = null;
	
    @Min(1)
    @Parameter(description = "Number of records to return. Min: 1, default: 100")
    public int limit = 100;

    @Min(0)
    @Parameter(description = "Number of records to skip before returning. Default: 0, min: 0")
    public int offset = 0;

    @Parameter(description = "Column name to be sorted by, varies for each endpoint, default is id")
    public String sortBy = "id";

    @Parameter(description = "Direction of sorting (ASC or DESC). Default DESC.")
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
	
