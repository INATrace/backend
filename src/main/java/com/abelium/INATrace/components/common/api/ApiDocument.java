package com.abelium.INATrace.components.common.api;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiDocument extends ApiBaseEntity {

    @NotNull
    @ApiModelProperty(value = "storage key (file on system, s3, ...).", position = 1)
    public String storageKey;
    
    @NotNull
    @ApiModelProperty(value = "document (file) name", position = 2)
    public String name;    

    @ApiModelProperty(value = "content type", position = 3)
    public String contentType;    
    
    @ApiModelProperty(value = "size", position = 4)
    public Long size;    

    
    public ApiDocument() {}

    public ApiDocument(Long id, String storageKey) {
        this.id = id;
    }

	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
}
