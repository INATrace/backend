package com.abelium.INATrace.components.product.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiPaginatedRequest;
import io.swagger.annotations.ApiParam;

@Validated
public class ApiListCustomersRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Name")
	public String query;
	
	@ApiParam(value = "Phone number")
	public String phone;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
