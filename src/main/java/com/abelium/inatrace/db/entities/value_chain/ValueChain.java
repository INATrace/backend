package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.value_chain.enums.ValueChainStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Value chain entity representing definition of generic value chains. This is used as a template when creating products.
 * It contains the relations with the supported facility types, grade abbreviations, measure unit types, processing
 * evidence types and semi products.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class ValueChain extends TimestampEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Lob
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = Lengths.ENUM)
	private ValueChainStatus valueChainStatus;

	/**
	 * Holds a list of supported facility types ba this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainFacilityType> facilityTypes;

	/**
	 * Holds a list of supported measuring unit types by this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainMeasureUnitType> measureUnitTypes;

	/**
	 * Holds a list of supported grade abbreviations by this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainGradeAbbreviation> gradeAbbreviations;

	/**
	 * Holds a list of supported processing evidence by this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainProcEvidenceType> procEvidenceTypes;

	/**
	 * Holds a list of supported semi-products by this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainSemiProduct> semiProducts;
	
	/**
	 * Holds a list of supported processing evidence fields by this value chain.
	 */
	@OneToMany(mappedBy = "valueChain", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainProcessingEvidenceField> processingEvidenceFields;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;

	/**
	 * Reference to the product type that this Value chain is based on.
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ProductType productType;

	@Version
	private long entityVersion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ValueChainStatus getValueChainStatus() {
		return valueChainStatus;
	}

	public void setValueChainStatus(ValueChainStatus valueChainStatus) {
		this.valueChainStatus = valueChainStatus;
	}

	public List<ValueChainFacilityType> getFacilityTypes() {
		if (facilityTypes == null) {
			facilityTypes = new ArrayList<>();
		}
		return facilityTypes;
	}

	public void setFacilityTypes(List<ValueChainFacilityType> facilityTypes) {
		this.facilityTypes = facilityTypes;
	}

	public List<ValueChainMeasureUnitType> getMeasureUnitTypes() {
		if (measureUnitTypes == null) {
			measureUnitTypes = new ArrayList<>();
		}
		return measureUnitTypes;
	}

	public void setMeasureUnitTypes(List<ValueChainMeasureUnitType> measureUnitTypes) {
		this.measureUnitTypes = measureUnitTypes;
	}

	public List<ValueChainGradeAbbreviation> getGradeAbbreviations() {
		if (gradeAbbreviations == null) {
			gradeAbbreviations = new ArrayList<>();
		}
		return gradeAbbreviations;
	}

	public void setGradeAbbreviations(List<ValueChainGradeAbbreviation> gradeAbbreviations) {
		this.gradeAbbreviations = gradeAbbreviations;
	}

	public List<ValueChainSemiProduct> getSemiProducts() {
		if (semiProducts == null) {
			semiProducts = new ArrayList<>();
		}
		return semiProducts;
	}

	public void setSemiProducts(List<ValueChainSemiProduct> semiProducts) {
		this.semiProducts = semiProducts;
	}
	
	public List<ValueChainProcessingEvidenceField> getProcessingEvidenceFields() {
		if (processingEvidenceFields == null) {
			processingEvidenceFields = new ArrayList<>();
		}
		return processingEvidenceFields;
	}

	public void setProcessingEvidenceFields(List<ValueChainProcessingEvidenceField> processingEvidenceFields) {
		this.processingEvidenceFields = processingEvidenceFields;
	}

	public List<ValueChainProcEvidenceType> getProcEvidenceTypes() {
		if (procEvidenceTypes == null) {
			procEvidenceTypes = new ArrayList<>();
		}
		return procEvidenceTypes;
	}

	public void setProcEvidenceTypes(List<ValueChainProcEvidenceType> procEvidenceTypes) {
		this.procEvidenceTypes = procEvidenceTypes;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public long getEntityVersion() {
		return entityVersion;
	}

	public void setEntityVersion(long entityVersion) {
		this.entityVersion = entityVersion;
	}

}
