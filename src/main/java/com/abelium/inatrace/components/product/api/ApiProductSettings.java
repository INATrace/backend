package com.abelium.inatrace.components.product.api;

import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductSettings {

	@ApiModelProperty(value = "check authenticity", position = 1)
	public Boolean checkAuthenticity;
	
	@ApiModelProperty(value = "trace origin", position = 2)
	public Boolean traceOrigin;
	
	@ApiModelProperty(value = "give feedback", position = 3)
	public Boolean giveFeedback;

	@ApiModelProperty(value = "cost breakdown", position = 4)
	public Boolean costBreakdown;

	@ApiModelProperty(value = "increase of coffee", position = 5)
	public Double increaseOfCoffee;
	
	@ApiModelProperty(value = "pricing transparency - string-number map", position = 6)
    public Map<String, Double> pricingTransparency;
	
	@ApiModelProperty(value = "increase in income - document", position = 7)
	public ApiDocument incomeIncreaseDocument;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "increase in income - description", position = 8)
	public String incomeIncreaseDescription;
	
	@ApiModelProperty(value = "language", position = 9)
	public Language language;

	@Length(max = 2000)
	@ApiModelProperty(value = "GDPR text", position = 10)
	public String gdprText;
	
	@Length(max = 50_000)
	@ApiModelProperty(value = "Privacy policy text", position = 11)
	public String privacyPolicyText;
	
	@Length(max = 50_000)
	@ApiModelProperty(value = "Terms of use text", position = 12)
	public String termsOfUseText;

	
	public Boolean getCheckAuthenticity() {
		return checkAuthenticity;
	}

	public void setCheckAuthenticity(Boolean checkAuthenticity) {
		this.checkAuthenticity = checkAuthenticity;
	}

	public Boolean getTraceOrigin() {
		return traceOrigin;
	}

	public void setTraceOrigin(Boolean traceOrigin) {
		this.traceOrigin = traceOrigin;
	}

	public Boolean getGiveFeedback() {
		return giveFeedback;
	}

	public void setGiveFeedback(Boolean giveFeedback) {
		this.giveFeedback = giveFeedback;
	}

	public Boolean getCostBreakdown() {
		return costBreakdown;
	}

	public void setCostBreakdown(Boolean costBreakdown) {
		this.costBreakdown = costBreakdown;
	}

	public Double getIncreaseOfCoffee() {
		return increaseOfCoffee;
	}

	public void setIncreaseOfCoffee(Double increaseOfCoffee) {
		this.increaseOfCoffee = increaseOfCoffee;
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
