package com.abelium.INATrace.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.UserRole;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiUserRole extends ApiBaseEntity {
	
	@ApiModelProperty(value = "Role", position = 1)
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
