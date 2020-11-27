package com.abelium.INATrace.api;

import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

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
    
    
    @ApiModelProperty(value = "Response items.", position = 0)
    public List<DATATYPE> items;
    
    @ApiModelProperty(value = "Count of all items satisfying 'paginatable' request.", position = 1)
    public int count;

    @ApiModelProperty(value = "Offset got from request", position = 2)
    public int offset;
    
    @ApiModelProperty(value = "Limit got from request", position = 3)
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
