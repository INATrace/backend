package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompanyDocument;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Validated
public class ApiProductLabelValuesExtended extends ApiProductLabelValues {
	
	@Schema(description = "Number of batches")
	public Integer numberOfBatches;
	
	@Schema(description = "Number of true 'checkAuthenticity' fields over all of batches")
	public Integer checkAuthenticityCount;

	@Schema(description = "Number of true 'traceOrigin' fields over all of batches")
	public Integer traceOriginCount;

	@Schema(description = "B2C settings")
	public ApiBusinessToCustomerSettings businessToCustomerSettings;

	@Schema(description = "Meet the farmers video")
	public ApiCompanyDocument videoMeetTheFarmers;

	@Schema(description = "Meet the farmers photos")
	public List<ApiCompanyDocument> photosMeetTheFarmers;

	@Schema(description = "Production records")
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
