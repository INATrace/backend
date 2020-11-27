package com.abelium.INATrace.components.common.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiDocumentStorageKey {

    @NotNull
    @ApiModelProperty(value = "storage key (file on system, s3, ...).", position = 1)
    public String storageKey;
    
    
	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

}
