package com.abelium.inatrace.components.processingevidencefield.api;

import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;

import io.swagger.annotations.ApiModelProperty;

/**
 * Processing evidence field API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiProcessingEvidenceField extends ApiBaseEntity {

	@ApiModelProperty(value = "Processing evidence field label")
	private String label;
	
	@ApiModelProperty(value = "Processing evidence field required")
	private Boolean required;
	
	@ApiModelProperty(value = "Processing evidence field mandatory")
	private Boolean mandatory;
	
	@ApiModelProperty(value = "Processing evidence field required on quote")
	private Boolean requiredOnQuote;
	
	@ApiModelProperty(value = "Processing evidence field numeric value")
	private Integer numericValue;
	
	@ApiModelProperty(value = "Processing evidence field string value")
	private String stringValue;
	
	@ApiModelProperty(value = "Processing evidence field file multiplicity")
	private Integer fileMultiplicity;
	
	@ApiModelProperty(value = "Processing evidence field type")
	private ProcessingEvidenceFieldType type;
	
	@ApiModelProperty(value = "Processing evidence field files info")
	private List<ApiFileInfo> files = new ArrayList<>();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
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

	public Integer getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(Integer numericValue) {
		this.numericValue = numericValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

//	public Object getObjectValue() {
//		return objectValue;
//	}
//
//	public void setObjectValue(Object objectValue) {
//		this.objectValue = objectValue;
//	}

	public Integer getFileMultiplicity() {
		return fileMultiplicity;
	}

	public void setFileMultiplicity(Integer fileMultiplicity) {
		this.fileMultiplicity = fileMultiplicity;
	}

	public ProcessingEvidenceFieldType getType() {
		return type;
	}

	public void setType(ProcessingEvidenceFieldType type) {
		this.type = type;
	}

	public List<ApiFileInfo> getFiles() {
		return files;
	}

	public void setFiles(List<ApiFileInfo> files) {
		this.files = files;
	}

	public ApiProcessingEvidenceField(String label, Boolean required, Boolean mandatory, Boolean requiredOnQuote,
			Integer numericValue, String stringValue, Integer fileMultiplicity, ProcessingEvidenceFieldType type,
			List<ApiFileInfo> files) {
		super();
		this.label = label;
		this.required = required;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
		this.numericValue = numericValue;
		this.stringValue = stringValue;
//		this.objectValue = objectValue;
		this.fileMultiplicity = fileMultiplicity;
		this.type = type;
		this.files = files;
	}

	public ApiProcessingEvidenceField() {
		super();
	}

}
