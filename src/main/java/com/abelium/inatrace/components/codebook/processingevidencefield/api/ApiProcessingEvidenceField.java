package com.abelium.inatrace.components.codebook.processingevidencefield.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Processing evidence field API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiProcessingEvidenceField extends ApiBaseEntity {

	@Schema(description = "Processing evidence field name")
	private String fieldName;

	@Schema(description = "Processing evidence field label")
	private String label;
	
	@Schema(description = "Processing evidence field mandatory")
	private Boolean mandatory;
	
	@Schema(description = "Processing evidence field required on quote")
	private Boolean requiredOnQuote;
	
	@Schema(description = "Processing evidence field type")
	private ProcessingEvidenceFieldType type;

	@Schema(description = "Processing evidence field translations")
	private List<ApiProcessingEvidenceFieldTranslation> translations;

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

	public List<ApiProcessingEvidenceFieldTranslation> getTranslations() {
		if (translations == null) {
			translations = new ArrayList<>();
		}
		return translations;
	}

	public void setTranslations(List<ApiProcessingEvidenceFieldTranslation> translations) {
		this.translations = translations;
	}

	public ApiProcessingEvidenceField(String label, Boolean mandatory, Boolean requiredOnQuote,
									  ProcessingEvidenceFieldType type, List<ApiProcessingEvidenceFieldTranslation> translations) {
		super();
		this.label = label;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
		this.type = type;
		this.translations = translations;
	}

	public ApiProcessingEvidenceField() {
		super();
	}

}
