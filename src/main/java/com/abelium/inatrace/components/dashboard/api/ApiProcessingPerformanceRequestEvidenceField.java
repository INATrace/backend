package com.abelium.inatrace.components.dashboard.api;

import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;

import java.math.BigDecimal;
import java.time.Instant;

public class ApiProcessingPerformanceRequestEvidenceField {
	private ApiProcessingEvidenceField evidenceField;
	private String stringValue;
	private Instant instantValue;
	private BigDecimal numericValue;

	public ApiProcessingEvidenceField getEvidenceField() {
		return evidenceField;
	}

	public void setEvidenceField(ApiProcessingEvidenceField evidenceField) {
		this.evidenceField = evidenceField;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Instant getInstantValue() {
		return instantValue;
	}

	public void setInstantValue(Instant instantValue) {
		this.instantValue = instantValue;
	}

	public BigDecimal getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(BigDecimal numericValue) {
		this.numericValue = numericValue;
	}
}
