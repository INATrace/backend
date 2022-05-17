package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompanyDocument;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Validated
public class ApiProductLabelValuesExtended extends ApiProductLabelValues {
	
	@ApiModelProperty(value = "Number of batches", position = 10)
	public Integer numberOfBatches;
	
	@ApiModelProperty(value = "Number of true 'checkAuthenticity' fields over all of batches", position = 11)
	public Integer checkAuthenticityCount;

	@ApiModelProperty(value = "Number of true 'traceOrigin' fields over all of batches", position = 11)
	public Integer traceOriginCount;

	@ApiModelProperty(value = "B2C settings")
	public ApiBusinessToCustomerSettings businessToCustomerSettings;

	@ApiModelProperty(value = "Meet the farmers video")
	public ApiCompanyDocument videoMeetTheFarmers;

	@ApiModelProperty(value = "Meet the farmers photos")
	public List<ApiCompanyDocument> photosMeetTheFarmers;

	@ApiModelProperty(value = "Production records")
	public List<ApiCompanyDocument> productionRecords;

	public Integer getNumberOfBatches() {
		return numberOfBatches;
	}

	public void setNumberOfBatches(Integer numberOfBatches) {
		this.numberOfBatches = numberOfBatches;
	}

	public Integer getCheckAuthenticityCount() {
		return checkAuthenticityCount;
	}

	public void setCheckAuthenticityCount(Integer checkAuthenticityCount) {
		this.checkAuthenticityCount = checkAuthenticityCount;
	}

	public Integer getTraceOriginCount() {
		return traceOriginCount;
	}

	public void setTraceOriginCount(Integer traceOriginCount) {
		this.traceOriginCount = traceOriginCount;
	}

	public ApiBusinessToCustomerSettings getBusinessToCustomerSettings() {
		return businessToCustomerSettings;
	}

	public void setBusinessToCustomerSettings(ApiBusinessToCustomerSettings businessToCustomerSettings) {
		this.businessToCustomerSettings = businessToCustomerSettings;
	}

	public ApiCompanyDocument getVideoMeetTheFarmers() {
		return videoMeetTheFarmers;
	}

	public void setVideoMeetTheFarmers(ApiCompanyDocument videoMeetTheFarmers) {
		this.videoMeetTheFarmers = videoMeetTheFarmers;
	}

	public List<ApiCompanyDocument> getPhotosMeetTheFarmers() {
		return photosMeetTheFarmers;
	}

	public void setPhotosMeetTheFarmers(List<ApiCompanyDocument> photosMeetTheFarmers) {
		this.photosMeetTheFarmers = photosMeetTheFarmers;
	}

	public List<ApiCompanyDocument> getProductionRecords() {
		return productionRecords;
	}

	public void setProductionRecords(List<ApiCompanyDocument> productionRecords) {
		this.productionRecords = productionRecords;
	}
}
