package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.ProductLabelFeedbackType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;
import java.util.Map;

@Validated
public class ApiProductLabelFeedback extends ApiBaseEntity {

	@Schema(description = "Label id")
	public Long labelId;
	
	@Schema(description = "Feedback type")
    public ProductLabelFeedbackType type;

	@Schema(description = "Email")
	@Size(max = Lengths.EMAIL)
	public String email;
	
	@Schema(description = "feedback text")
	@Size(max = 2000)
	public String feedback;

	@Schema(description = "GDPR consent")
	public Boolean gdprConsent;
	
	@Schema(description = "Privacy policy consent")
	public Boolean privacyPolicyConsent;

	@Schema(description = "Terms of use consent")
	public Boolean termsOfUseConsent;
	
	@Schema(description = "questionnaire answers - key-answer map")
    public Map<String, String> questionnaireAnswers;

	@Schema(description = "The product name of label for which the feedback was provided")
	private String productName;
	
	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
