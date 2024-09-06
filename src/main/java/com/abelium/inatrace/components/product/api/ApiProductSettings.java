package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.Map;

@Validated
public class ApiProductSettings {

	@ApiModelProperty(value = "cost breakdown", position = 4)
	public Boolean costBreakdown;
	
	@ApiModelProperty(value = "pricing transparency - string-number map", position = 6)
    public Map<String, Double> pricingTransparency;
	
	@ApiModelProperty(value = "increase in income - document", position = 7)
	public ApiDocument incomeIncreaseDocument;
	
	@Size(max = 2000)
	@ApiModelProperty(value = "increase in income - description", position = 8)
	public String incomeIncreaseDescription;
	
	@ApiModelProperty(value = "language", position = 9)
	public Language language;

	@Size(max = 2000)
	@ApiModelProperty(value = "GDPR text", position = 10)
	public String gdprText;
	
	@Size(max = 50_000)
	@ApiModelProperty(value = "Privacy policy text", position = 11)
	public String privacyPolicyText;
	
	@Size(max = 50_000)
	@ApiModelProperty(value = "Terms of use text", position = 12)
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
