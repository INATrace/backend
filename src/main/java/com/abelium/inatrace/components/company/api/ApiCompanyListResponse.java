package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.CompanyStatus;

import io.swagger.annotations.ApiModelProperty;

public class ApiCompanyListResponse extends ApiBaseEntity {

	@ApiModelProperty(value = "Company status")
	public CompanyStatus status;
	
	@ApiModelProperty(value = "Name")
	public String name;
	
    @ApiModelProperty(value = "storage key (file on system, s3, ...).")
    public String logoStorageKey;


	public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoStorageKey() {
		return logoStorageKey;
	}

	public void setLogoStorageKey(String logoStorageKey) {
		this.logoStorageKey = logoStorageKey;
	}
}
