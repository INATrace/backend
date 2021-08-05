package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class ApiProductListResponse extends ApiBaseEntity {

	@ApiModelProperty(value = "Name")
	public String name;

	@ApiModelProperty(value = "Photo storage id")
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
