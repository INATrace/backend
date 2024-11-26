package com.abelium.inatrace.components.user.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.UserRole;
import com.abelium.inatrace.types.UserStatus;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;

@Validated
@ParameterObject
public class ApiListUsersRequest extends ApiPaginatedRequest {

	@Parameter(description = "Status")
	public UserStatus status;
	
	@Parameter(description = "User role")
	public UserRole role;
	
	@Parameter(description = "Email")
	public String email;
	
	@Parameter(description = "Surname")
	public String surname;

	@Parameter(description = "Name, surname or email")
	public String query;
	
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
