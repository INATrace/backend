package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListCustomersRequest extends ApiPaginatedRequest {

	@Parameter(description = "Name")
	public String query;
	
	@Parameter(description = "Phone number")
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
