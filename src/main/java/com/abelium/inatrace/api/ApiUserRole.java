package com.abelium.inatrace.api;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.types.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserRole extends ApiBaseEntity {
	
	@Schema(description = "Role")
	public UserRole role;
	
	public ApiUserRole() {
	}

	public ApiUserRole(Long id) {
		super(id);
	}

	public ApiUserRole(BaseEntity baseEntity) {
		super(baseEntity);
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
