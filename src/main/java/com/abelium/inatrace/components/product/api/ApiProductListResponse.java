package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiProductListResponse extends ApiBaseEntity {

	@Schema(description = "Name")
	public String name;

	@Schema(description = "Photo storage id")
	public String photoStorageId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoStorageId() {
		return photoStorageId;
	}

	public void setPhotoStorageId(String photoStorageId) {
		this.photoStorageId = photoStorageId;
	}
}
