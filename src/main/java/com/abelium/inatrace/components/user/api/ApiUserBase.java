package com.abelium.inatrace.components.user.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.UserRole;
import com.abelium.inatrace.types.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiUserBase extends ApiBaseEntity {

	@Schema(description = "Email/username")
	public String email;
	
	@Schema(description = "Name")
	public String name;
	
	@Schema(description = "Surname")
	public String surname;
	
	@Schema(description = "Status")
	public UserStatus status;
	
	@Schema(description = "User role")
	public UserRole role;
	
	@Schema(description = "language")
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
