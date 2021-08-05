package com.abelium.inatrace.components.user.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.types.UserRole;
import com.abelium.inatrace.types.UserStatus;

import io.swagger.annotations.ApiParam;

@Validated
public class ApiListUsersRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Status")
	public UserStatus status;
	
	@ApiParam(value = "User role")
	public UserRole role;
	
	@ApiParam(value = "Email")
	public String email;
	
	@ApiParam(value = "Surname")
	public String surname;

	@ApiParam(value = "Name, surname or email")
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
