package com.abelium.INATrace.components.user.api;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.types.Language;
import com.abelium.INATrace.types.UserRole;
import com.abelium.INATrace.types.UserStatus;

import io.swagger.annotations.ApiModelProperty;

public class ApiUserBase extends ApiBaseEntity {

	@ApiModelProperty("Email/username")
	public String email;
	
	@ApiModelProperty(value = "Name")
	public String name;
	
	@ApiModelProperty(value = "Surname")
	public String surname;
	
	@ApiModelProperty(value = "Status")
	public UserStatus status;
	
	@ApiModelProperty(value = "User role")
	public UserRole role;
	
	@ApiModelProperty(value = "language")
	public Language language;	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
