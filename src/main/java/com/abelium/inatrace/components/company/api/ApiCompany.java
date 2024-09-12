package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ApiCompany extends ApiCompanyBase {

	@Schema(description = "high-resolution logo of the company (jpeg, jpg, png)")
	public ApiDocument logo;
	
	@Size(max = 2000)
	@Schema(description = "interview", maxLength = 2000)
	public String interview;

	@Schema(description = "company documents")
	@Valid
	public List<ApiCompanyDocument> documents;	

	@Schema(description = "company certifications")
	@Valid
	public List<ApiCertification> certifications;

	@Schema(description = "company value chains")
	@Valid
	public List<ApiValueChain> valueChains;

	@Schema(description = "Preferred currency of the company")
	@Valid
	public ApiCurrencyType currency;

	@Schema(description = "Is company allowed to export orders to Beyco platform")
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
