package com.abelium.inatrace.db.entities.processingevidencefield;

import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FileInfo extends BaseEntity {

	@Column
	private String storageKey;
	
	@Column
	private String name;
	
	@Column
	private String contentType;
	
	@Column
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
	
	public FileInfo(String storageKey, String name, String contentType, Integer size) {
		super();
		this.storageKey = storageKey;
		this.name = name;
		this.contentType = contentType;
		this.size = size;
	}

	public FileInfo() {
		super();
	}
	
}
