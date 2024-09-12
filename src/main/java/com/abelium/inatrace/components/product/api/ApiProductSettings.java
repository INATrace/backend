package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;
import java.util.Map;

@Validated
public class ApiProductSettings {

	@Schema(description = "cost breakdown")
	public Boolean costBreakdown;
	
	@Schema(description = "pricing transparency - string-number map")
    public Map<String, Double> pricingTransparency;
	
	@Schema(description = "increase in income - document")
	public ApiDocument incomeIncreaseDocument;
	
	@Size(max = 2000)
	@Schema(description = "increase in income - description", maxLength = 2000)
	public String incomeIncreaseDescription;
	
	@Schema(description = "language")
	public Language language;

	@Size(max = 2000)
	@Schema(description = "GDPR text", maxLength = 2000)
	public String gdprText;
	
	@Size(max = 50_000)
	@Schema(description = "Privacy policy text", maxLength = 50_000)
	public String privacyPolicyText;
	
	@Size(max = 50_000)
	@Schema(description = "Terms of use text", maxLength = 50_000)
	public String termsOfUseText;

	public Boolean getCostBreakdown() {
		return costBreakdown;
	}

	public void setCostBreakdown(Boolean costBreakdown) {
		this.costBreakdown = costBreakdown;
	}

	public Map<String, Double> getPricingTransparency() {
		return pricingTransparency;
	}

	public void setPricingTransparency(Map<String, Double> pricingTransparency) {
		this.pricingTransparency = pricingTransparency;
	}

	public ApiDocument getIncomeIncreaseDocument() {
		return incomeIncreaseDocument;
	}

	public void setIncomeIncreaseDocument(ApiDocument incomeIncreaseDocument) {
		this.incomeIncreaseDocument = incomeIncreaseDocument;
	}

	public String getIncomeIncreaseDescription() {
		return incomeIncreaseDescription;
	}

	public void setIncomeIncreaseDescription(String incomeIncreaseDescription) {
		this.incomeIncreaseDescription = incomeIncreaseDescription;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getGdprText() {
		return gdprText;
	}

	public void setGdprText(String gdprText) {
		this.gdprText = gdprText;
	}

	public String getPrivacyPolicyText() {
		return privacyPolicyText;
	}

	public void setPrivacyPolicyText(String privacyPolicyText) {
		this.privacyPolicyText = privacyPolicyText;
	}

	public String getTermsOfUseText() {
		return termsOfUseText;
	}

	public void setTermsOfUseText(String termsOfUseText) {
		this.termsOfUseText = termsOfUseText;
	}

}
