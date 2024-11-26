package com.abelium.inatrace.components.codebook.processing_evidence_type.api;

import com.abelium.inatrace.api.ApiCodebookBaseEntity;
import com.abelium.inatrace.db.enums.EvidenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Processing evidence type API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiProcessingEvidenceType extends ApiCodebookBaseEntity {

	@Schema(description = "type of evidence")
	private EvidenceType type;

	@Schema(description = "if evidence is of fairness type")
	private Boolean fairness;

	@Schema(description = "if evidence is of provenance type")
	private Boolean provenance;

	@Schema(description = "if evidence is of quality type")
	private Boolean quality;

	@Schema(description = "whether the evidence is mandatory")
	private Boolean mandatory;

	@Schema(description = "whether the evidence is required on quote")
	private Boolean requiredOnQuote;

	@Schema(description = "a group in which at least one document has to be provided")
	private String requiredOneOfGroupIdForQuote;

	@Schema(description = "Translations for processing evidence type")
	private List<ApiProcessingEvidenceTypeTranslation> translations;

	public EvidenceType getType() {
		return type;
	}

	public void setType(EvidenceType type) {
		this.type = type;
	}

	public Boolean getFairness() {
		return fairness;
	}

	public void setFairness(Boolean fairness) {
		this.fairness = fairness;
	}

	public Boolean getProvenance() {
		return provenance;
	}

	public void setProvenance(Boolean provenance) {
		this.provenance = provenance;
	}

	public Boolean getQuality() {
		return quality;
	}

	public void setQuality(Boolean quality) {
		this.quality = quality;
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

	public String getRequiredOneOfGroupIdForQuote() {
		return requiredOneOfGroupIdForQuote;
	}

	public void setRequiredOneOfGroupIdForQuote(String requiredOneOfGroupIdForQuote) {
		this.requiredOneOfGroupIdForQuote = requiredOneOfGroupIdForQuote;
	}

	public List<ApiProcessingEvidenceTypeTranslation> getTranslations() {
		if (translations == null) {
			translations = new ArrayList<>();
		}
		return translations;
	}

	public void setTranslations(List<ApiProcessingEvidenceTypeTranslation> translations) {
		this.translations = translations;
	}
}
