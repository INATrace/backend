package com.abelium.inatrace.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

public class ApiPaginatedList<DATATYPE> {
    
    public ApiPaginatedList() {
        this.items = Collections.emptyList();
        this.count = 0;
    }
    
    public ApiPaginatedList(List<DATATYPE> items, int count) {
        this.items = items;
        this.count = count;
    }
    
    public ApiPaginatedList(List<DATATYPE> items, long count) {
        this(items, (int) count);
    }
    
    
    @Schema(description = "Response items.")
    public List<DATATYPE> items;
    
    @Schema(description = "Count of all items satisfying 'paginatable' request.")
    public int count;

    @Schema(description = "Offset got from request")
    public int offset;
    
    @Schema(description = "Limit got from request")
    public int limit;
    
    public List<DATATYPE> getItems() {
        return items;
    }

    public void setItems(List<DATATYPE> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
