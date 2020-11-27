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
import com.abelium.INATrace.db.base.TimestampEntity;
import com.abelium.INATrace.types.ProductLabelFeedbackType;

@Entity
public class ProductLabelFeedback extends TimestampEntity {
	
	/**
	 * label of this feedback
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductLabel label;
	
	/**
	 * feedback type
	 */
	@Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
    private ProductLabelFeedbackType type;
	
	/**
	 * email
	 */
	@Column(length = Lengths.EMAIL)
	private String email;
	
	/**
	 * feedback
	 */
	@Lob
	private String feedback;
	
	/**
	 * GDPR consent
	 */
	private Boolean gdprConsent;

	/**
	 * Privacy policy consent
	 */
	private Boolean privacyPolicyConsent;
	
	/**
	 * Terms of use consent
	 */
	private Boolean termsOfUseConsent;
	
	/**
	 * Questionnaire answers
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "questionKey")
    @Column(name = "answer")	
    @CollectionTable
    private Map<String, String> questionnaireAnswers = new HashMap<>();

	
	public ProductLabel getLabel() {
		return label;
	}

	public void setLabel(ProductLabel label) {
		this.label = label;
	}

	public ProductLabelFeedbackType getType() {
		return type;
	}

	public void setType(ProductLabelFeedbackType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Boolean getGdprConsent() {
		return gdprConsent;
	}

	public void setGdprConsent(Boolean gdprConsent) {
		this.gdprConsent = gdprConsent;
	}
	
	public Boolean getPrivacyPolicyConsent() {
		return privacyPolicyConsent;
	}

	public void setPrivacyPolicyConsent(Boolean privacyPolicyConsent) {
		this.privacyPolicyConsent = privacyPolicyConsent;
	}

	public Boolean getTermsOfUseConsent() {
		return termsOfUseConsent;
	}

	public void setTermsOfUseConsent(Boolean termsOfUseConsent) {
		this.termsOfUseConsent = termsOfUseConsent;
	}

	public Map<String, String> getQuestionnaireAnswers() {
		return questionnaireAnswers;
	}

	public void setQuestionnaireAnswers(Map<String, String> questionnaireAnswers) {
		this.questionnaireAnswers = questionnaireAnswers;
	}
	
	
}
