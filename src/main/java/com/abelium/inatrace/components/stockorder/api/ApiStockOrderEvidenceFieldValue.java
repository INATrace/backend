package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;

@Validated
public class ApiStockOrderEvidenceFieldValue extends ApiBaseEntity {

	@ApiModelProperty(value = "The id of the Processing evidence field from the codebook", position = 1)
	private Long evidenceFieldId;

	@ApiModelProperty(value = "The field name of the Processing evidence field from the codebook", position = 2)
	private String evidenceFieldName;

	@ApiModelProperty(value = "The data type oof the Processing evidence field from the codebook", position = 3)
	private ProcessingEvidenceFieldType evidenceFieldType;

	@ApiModelProperty(value = "Value holder of type String", position = 4)
	private String stringValue;

	@ApiModelProperty(value = "Value holder of type Number", position = 5)
	private BigDecimal numericValue;

	@ApiModelProperty(value = "Value holder of type Boolean", position = 6)
	private Boolean booleanValue;

	@ApiModelProperty(value = "Value holder of type Date", position = 7)
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
