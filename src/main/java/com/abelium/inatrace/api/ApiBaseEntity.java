package com.abelium.inatrace.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.db.base.BaseEntity;

@Validated
public class ApiBaseEntity {
	
	@Schema(description = "Entity id")
	public Long id;
	
	public ApiBaseEntity() {
	}

	public ApiBaseEntity(Long id) {
		this.id = id;
	}

	public ApiBaseEntity(BaseEntity baseEntity) {
		this.id = baseEntity != null ? baseEntity.getId() : null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
