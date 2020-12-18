package com.abelium.INATrace.components.product.api;

import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.types.ProductLabelFeedbackType;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelFeedback extends ApiBaseEntity {

	@ApiModelProperty(value = "Label id", position = 2)
	public Long labelId;
	
	@ApiModelProperty(value = "Feedback type", position = 3)
    public ProductLabelFeedbackType type;

	@ApiModelProperty(value = "Email", position = 4)
	@Length(max = Lengths.EMAIL)
	public String email;
	
	@ApiModelProperty(value = "feedback text", position = 5)
	@Length(max = 2000)
	public String feedback;

	@ApiModelProperty(value = "GDPR consent", position = 6)
	public Boolean gdprConsent;
	
	@ApiModelProperty(value = "Privacy policy consent", position = 7)
	public Boolean privacyPolicyConsent;

	@ApiModelProperty(value = "Terms of use consent", position = 8)
	public Boolean termsOfUseConsent;
	
	@ApiModelProperty(value = "questionnaire answers - key-answer map", position = 9)
    public Map<String, String> questionnaireAnswers;	
	
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
	
}
