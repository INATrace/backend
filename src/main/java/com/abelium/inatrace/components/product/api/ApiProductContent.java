package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.common.api.ApiDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiProductContent extends ApiProductBase {

	@Schema(description = "high-resolution photo of the product (jpeg, jpg, png), 72dpi and 1200x2000px recommended")
	public ApiDocument photo;	
	
	@Schema(description = "process")
	@Valid
	public ApiProcess process;
	
	@Schema(description = "social responsibility")
	@Valid
	public ApiResponsibility responsibility;
	
	@Schema(description = "environmental sustainability")
	@Valid
	public ApiSustainability sustainability;

	@Schema(description = "settings")
	@Valid
	public ApiProductSettings settings;

	@Schema(description = "B2C settings")
	private ApiBusinessToCustomerSettings businessToCustomerSettings;
	
	public ApiDocument getPhoto() {
		return photo;
	}

	public void setPhoto(ApiDocument photo) {
		this.photo = photo;
	}

	public ApiProcess getProcess() {
		return process;
	}

	public void setProcess(ApiProcess process) {
		this.process = process;
	}

	public ApiResponsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(ApiResponsibility responsibility) {
		this.responsibility = responsibility;
	}

	public ApiSustainability getSustainability() {
		return sustainability;
	}

	public void setSustainability(ApiSustainability sustainability) {
		this.sustainability = sustainability;
	}

	public ApiProductSettings getSettings() {
		return settings;
	}

	public void setSettings(ApiProductSettings settings) {
		this.settings = settings;
	}

	public ApiBusinessToCustomerSettings getBusinessToCustomerSettings() {
		return businessToCustomerSettings;
	}

	public void setBusinessToCustomerSettings(ApiBusinessToCustomerSettings businessToCustomerSettings) {
		this.businessToCustomerSettings = businessToCustomerSettings;
	}

}
