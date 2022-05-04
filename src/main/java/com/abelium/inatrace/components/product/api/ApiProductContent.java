package com.abelium.inatrace.components.product.api;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.components.common.api.ApiDocument;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductContent extends ApiProductBase {

	@ApiModelProperty(value = "high-resolution photo of the product (jpeg, jpg, png), 72dpi and 1200x2000px recommended", position = 3)
	public ApiDocument photo;	
	
	@ApiModelProperty(value = "process", position = 11)
	@Valid
	public ApiProcess process;
	
	@ApiModelProperty(value = "social responsibility", position = 12)
	@Valid
	public ApiResponsibility responsibility;
	
	@ApiModelProperty(value = "environmental sustainability", position = 13)
	@Valid
	public ApiSustainability sustainability;
	
	@ApiModelProperty(value = "speciality document", position = 16)
	public ApiDocument specialityDocument;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "speciality document", position = 17)
	public String specialityDescription;
	
	@ApiModelProperty(value = "settings", position = 18)
	@Valid
	public ApiProductSettings settings;

	@ApiModelProperty(value = "comparison of price", position = 19)
	@Valid
	public ApiComparisonOfPrice comparisonOfPrice;

	@ApiModelProperty(value = "knowledge blog", position = 20)
	public Boolean knowledgeBlog;

	@ApiModelProperty(value = "B2C settings", position = 21)
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

	public ApiDocument getSpecialityDocument() {
		return specialityDocument;
	}

	public void setSpecialityDocument(ApiDocument specialityDocument) {
		this.specialityDocument = specialityDocument;
	}

	public String getSpecialityDescription() {
		return specialityDescription;
	}

	public void setSpecialityDescription(String specialityDescription) {
		this.specialityDescription = specialityDescription;
	}

	public ApiProductSettings getSettings() {
		return settings;
	}

	public void setSettings(ApiProductSettings settings) {
		this.settings = settings;
	}

	public ApiComparisonOfPrice getComparisonOfPrice() {
		return comparisonOfPrice;
	}

	public void setComparisonOfPrice(ApiComparisonOfPrice comparisonOfPrice) {
		this.comparisonOfPrice = comparisonOfPrice;
	}

	public Boolean getKnowledgeBlog() {
		return knowledgeBlog;
	}

	public void setKnowledgeBlog(Boolean knowledgeBlog) {
		this.knowledgeBlog = knowledgeBlog;
	}

	public ApiBusinessToCustomerSettings getBusinessToCustomerSettings() {
		return businessToCustomerSettings;
	}

	public void setBusinessToCustomerSettings(ApiBusinessToCustomerSettings businessToCustomerSettings) {
		this.businessToCustomerSettings = businessToCustomerSettings;
	}
}
