package com.abelium.INATrace.db.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.Language;

@Entity
public class ProductSettings extends BaseEntity {

	/**
	 * check authenticity
	 */
	@Column
	private Boolean checkAuthenticity;

	/**
	 * trace origin
	 */
	@Column
	private Boolean traceOrigin;
	
	/**
	 * give feedback
	 */
	@Column
	private Boolean giveFeedback;

	/**
	 * cost breakdown
	 */
	@Column
	private Boolean costBreakdown;
	
	/**
	 * increase of coffee
	 */
	@Column
	private Double increaseOfCoffee;
	
	/**
	 * pricing transparency
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "ptKey")
    @Column(name = "ptValue")	
    @CollectionTable
    private Map<String, Double> pricingTransparency = new HashMap<>();
	
	/**
	 * increase in income - document
	 */
	@ManyToOne
	private Document incomeIncreaseDocument;

	/**
	 * increase in income - description
	 */
	@Lob
	private String incomeIncreaseDescription;
	
	/**
	 * default language
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
	private Language language = Language.EN;
	
	/**
	 * GDPR text
	 */
	@Lob
	private String gdprText;
	
	/**
	 * Privacy policy text
	 */
	@Lob
	private String privacyPolicyText;
	
	/**
	 * Terms of use text
	 */
	@Lob
	private String termsOfUseText;
	

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

	public Document getIncomeIncreaseDocument() {
		return incomeIncreaseDocument;
	}

	public void setIncomeIncreaseDocument(Document incomeIncreaseDocument) {
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

	public ProductSettings copy() {
		ProductSettings s = new ProductSettings();
		s.setCheckAuthenticity(getCheckAuthenticity());
		s.setTraceOrigin(getTraceOrigin());
		s.setGiveFeedback(getGiveFeedback());
		s.setCostBreakdown(getCostBreakdown());
		s.setIncreaseOfCoffee(getIncreaseOfCoffee());
		s.setPricingTransparency(new HashMap<>(getPricingTransparency()));
		s.setIncomeIncreaseDescription(getIncomeIncreaseDescription());
		s.setIncomeIncreaseDocument(getIncomeIncreaseDocument());
		s.setLanguage(getLanguage());
		s.setGdprText(getGdprText());
		s.setPrivacyPolicyText(getPrivacyPolicyText());
		s.setTermsOfUseText(getTermsOfUseText());
		return s;
	}
}

