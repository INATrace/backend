package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;

@Validated
public class ApiStockOrderEvidenceFieldValue extends ApiBaseEntity {

	@Schema(description = "The id of the Processing evidence field from the codebook")
	private Long evidenceFieldId;

	@Schema(description = "The field name of the Processing evidence field from the codebook")
	private String evidenceFieldName;

	@Schema(description = "The data type oof the Processing evidence field from the codebook")
	private ProcessingEvidenceFieldType evidenceFieldType;

	@Schema(description = "Value holder of type String")
	private String stringValue;

	@Schema(description = "Value holder of type Number")
	private BigDecimal numericValue;

	@Schema(description = "Value holder of type Boolean")
	private Boolean booleanValue;

	@Schema(description = "Value holder of type Date")
	private Instant dateValue;

	public Long getEvidenceFieldId() {
		return evidenceFieldId;
	}

	public void setEvidenceFieldId(Long evidenceFieldId) {
		this.evidenceFieldId = evidenceFieldId;
	}

	public String getEvidenceFieldName() {
		return evidenceFieldName;
	}

	public void setEvidenceFieldName(String evidenceFieldName) {
		this.evidenceFieldName = evidenceFieldName;
	}

	public ProcessingEvidenceFieldType getEvidenceFieldType() {
		return evidenceFieldType;
	}

	public void setEvidenceFieldType(ProcessingEvidenceFieldType evidenceFieldType) {
		this.evidenceFieldType = evidenceFieldType;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public BigDecimal getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(BigDecimal numericValue) {
		this.numericValue = numericValue;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public Instant getDateValue() {
		return dateValue;
	}

	public void setDateValue(Instant dateValue) {
		this.dateValue = dateValue;
	}

}
