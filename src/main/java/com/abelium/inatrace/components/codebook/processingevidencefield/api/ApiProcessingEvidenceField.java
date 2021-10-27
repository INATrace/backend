package com.abelium.inatrace.components.codebook.processingevidencefield.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;

import io.swagger.annotations.ApiModelProperty;

/**
 * Processing evidence field API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiProcessingEvidenceField extends ApiBaseEntity {

	@ApiModelProperty(value = "Processing evidence field name")
	private String fieldName;

	@ApiModelProperty(value = "Processing evidence field label")
	private String label;
	
	@ApiModelProperty(value = "Processing evidence field mandatory")
	private Boolean mandatory;
	
	@ApiModelProperty(value = "Processing evidence field required on quote")
	private Boolean requiredOnQuote;
	
	@ApiModelProperty(value = "Processing evidence field type")
	private ProcessingEvidenceFieldType type;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getRequiredOnQuote() {
		return requiredOnQuote;
	}

	public void setRequiredOnQuote(Boolean requiredOnQuote) {
		this.requiredOnQuote = requiredOnQuote;
	}

	public ProcessingEvidenceFieldType getType() {
		return type;
	}

	public void setType(ProcessingEvidenceFieldType type) {
		this.type = type;
	}

	public ApiProcessingEvidenceField(String label, Boolean mandatory, Boolean requiredOnQuote,
	                                  ProcessingEvidenceFieldType type) {
		super();
		this.label = label;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
		this.type = type;
	}

	public ApiProcessingEvidenceField() {
		super();
	}

}
