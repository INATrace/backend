package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.enums.EvidenceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Codebook entity for processing evidences.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class ProcessingEvidenceType extends TimestampEntity {

	/**
	 * Capitalized underscored string that defines the document requirement.
	 */
	@Column(nullable = false)
	private String code;

	/**
	 * Default english label.
	 */
	@Column(nullable = false)
	private String label;

	/**
	 * Type of evidence type. DOCUMENT is prescribed (date, type, document). FIELD is any other. Some others can be added.
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private EvidenceType type;

	/**
	 * Whether the evidence is of fairness type.
	 */
	private Boolean fairness;

	/**
	 * Whether the evidence is of provenance type.
	 */
	private Boolean provenance;

	/**
	 * Whether the evidence is of quality type.
	 */
	private Boolean quality;

	/**
	 * Whether the evidence is required (not used for settings, just in transformations).
	 */
	private Boolean required;

	/**
	 * Whether the evidence is required on quote (not used for settings, just in transformations).
	 */
	private Boolean requiredOnQuote;

	/**
	 * Defines a group in which at least one document has to be provided (is required).
	 */
	private String requiredOneOfGroupIdForQuote;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
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
}