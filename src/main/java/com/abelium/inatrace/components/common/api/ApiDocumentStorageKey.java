package com.abelium.inatrace.components.common.api;

import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiDocumentStorageKey {

    @NotNull
    @Schema(description = "storage key (file on system, s3, ...).")
    public String storageKey;
    
    
	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

}
