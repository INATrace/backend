package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListCollectorsRequest extends ApiPaginatedRequest {

	@Parameter(name = "Name or surname")
	public String query;
	
	@Parameter(name = "Phone number")
	public String phone;

	@Parameter(name = "Type (collector, farmer)")
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
