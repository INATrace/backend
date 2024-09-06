package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public class ApiCompany extends ApiCompanyBase {

	@ApiModelProperty(value = "high-resolution logo of the company (jpeg, jpg, png)", position = 3)
	public ApiDocument logo;
	
	@Size(max = 2000)
	@ApiModelProperty(value = "interview", position = 11)
	public String interview;

	@ApiModelProperty(value = "company documents", position = 12)
	@Valid
	public List<ApiCompanyDocument> documents;	

	@ApiModelProperty(value = "company certifications", position = 13)
	@Valid
	public List<ApiCertification> certifications;

	@ApiModelProperty(value = "company value chains", position = 14)
	@Valid
	public List<ApiValueChain> valueChains;

	@ApiModelProperty(value = "Preferred currency of the company")
	@Valid
	public ApiCurrencyType currency;

	@ApiModelProperty(value = "Is company allowed to export orders to Beyco platform")
	@Valid
	public Boolean allowBeycoIntegration;
	
	public ApiDocument getLogo() {
		return logo;
	}

	public void setLogo(ApiDocument logo) {
		this.logo = logo;
	}

	public String getInterview() {
		return interview;
	}

	public void setInterview(String interview) {
		this.interview = interview;
	}

	public List<ApiCompanyDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ApiCompanyDocument> documents) {
		this.documents = documents;
	}

	public List<ApiCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<ApiCertification> certifications) {
		this.certifications = certifications;
	}

	public List<ApiValueChain> getValueChains() {
		return valueChains;
	}

	public void setValueChains(List<ApiValueChain> valueChains) {
		this.valueChains = valueChains;
	}

	public ApiCurrencyType getCurrency() {
		return currency;
	}

	public void setCurrency(ApiCurrencyType currency) {
		this.currency = currency;
	}

	public Boolean getAllowBeycoIntegration() {
		return allowBeycoIntegration;
	}

	public void setAllowBeycoIntegration(Boolean allowBeycoIntegration) {
		this.allowBeycoIntegration = allowBeycoIntegration;
	}
}
