package com.abelium.INATrace.api;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.db.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiBaseEntity {
	
	@ApiModelProperty(value = "Entity id", position = 0)
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
