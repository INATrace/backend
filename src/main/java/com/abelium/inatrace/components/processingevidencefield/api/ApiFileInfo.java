package com.abelium.inatrace.components.processingevidencefield.api;

import com.abelium.inatrace.api.ApiBaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * File info API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiFileInfo extends ApiBaseEntity {

	@ApiModelProperty(value = "File info storage key")
	private String storageKey;
	
	@ApiModelProperty(value = "File info name")
	private String name;
	
	@ApiModelProperty(value = "File info content type")
	private String contentType;
	
	@ApiModelProperty(value = "File info size")
	private Integer size;

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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public ApiFileInfo(String storageKey, String name, String contentType, Integer size) {
		super();
		this.storageKey = storageKey;
		this.name = name;
		this.contentType = contentType;
		this.size = size;
	}

	public ApiFileInfo() {
		super();
	}
	
}
