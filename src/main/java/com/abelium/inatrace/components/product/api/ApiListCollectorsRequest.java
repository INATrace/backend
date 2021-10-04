package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.types.UserCustomerType;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.annotations.ApiParam;

@Validated
public class ApiListCollectorsRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Name or surname")
	public String query;
	
	@ApiParam(value = "Phone number")
	public String phone;

	@ApiParam(value = "Type (collector, farmer)")
	public UserCustomerType userCustomerType;

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

	public UserCustomerType getUserCustomerType() {
		return userCustomerType;
	}

	public void setUserCustomerType(UserCustomerType userCustomerType) {
		this.userCustomerType = userCustomerType;
	}
}
