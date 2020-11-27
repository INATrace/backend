package com.abelium.INATrace.components.product.api;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.components.common.api.ApiCertification;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProcess {

	@Length(max = 2000)
	@ApiModelProperty(value = "production description - Briefly describe your production process", position = 1)
	public String production;

	@Length(max = 2000)
	@ApiModelProperty(value = "storage - Briefly describe your storage procedures", position = 2)
	public String storage;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "codes of conduct - Briefly describe your company codes of conduct that your employees", position = 3)
	public String codesOfConduct;

	@ApiModelProperty(value = "certifications and standards", position = 4)
	@Valid
	public List<ApiCertification> standards;

	@ApiModelProperty(value = "production records", position = 5)
	@Valid
	public List<ApiProcessDocument> records;
	

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getCodesOfConduct() {
		return codesOfConduct;
	}

	public void setCodesOfConduct(String codesOfConduct) {
		this.codesOfConduct = codesOfConduct;
	}

	public List<ApiCertification> getStandards() {
		return standards;
	}

	public void setStandards(List<ApiCertification> standards) {
		this.standards = standards;
	}

	public List<ApiProcessDocument> getRecords() {
		return records;
	}

	public void setRecords(List<ApiProcessDocument> records) {
		this.records = records;
	}
}
