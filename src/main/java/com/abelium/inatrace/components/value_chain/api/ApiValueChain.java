package com.abelium.inatrace.components.value_chain.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.db.entities.value_chain.enums.ValueChainStatus;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Value chain API model entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiValueChain extends ApiBaseEntity {

	@ApiModelProperty(value = "name of the value chain", position = 2)
	private String name;

	@ApiModelProperty(value = "description of the value chain", position = 3)
	private String description;

	@ApiModelProperty(value = "value chain status", position = 4)
	private ValueChainStatus valueChainStatus;

	@ApiModelProperty(value = "list of supported facility types")
	private List<ApiFacilityType> facilityTypes;

	@ApiModelProperty(value = "list of supported measuring unit types")
	private List<ApiMeasureUnitType> measureUnitTypes;

	@ApiModelProperty(value = "list of supported grade abbreviations")
	private List<ApiGradeAbbreviation> gradeAbbreviations;

	@ApiModelProperty(value = "list of supported processing evidence types")
	private List<ApiProcessingEvidenceType> processingEvidenceTypes;

	@ApiModelProperty(value = "list of supported semi-products")
	private List<ApiSemiProduct> semiProducts;

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

	public List<ApiFacilityType> getFacilityTypes() {
		return facilityTypes;
	}

	public void setFacilityTypes(List<ApiFacilityType> facilityTypes) {
		this.facilityTypes = facilityTypes;
	}

	public List<ApiMeasureUnitType> getMeasureUnitTypes() {
		return measureUnitTypes;
	}

	public void setMeasureUnitTypes(List<ApiMeasureUnitType> measureUnitTypes) {
		this.measureUnitTypes = measureUnitTypes;
	}

	public List<ApiGradeAbbreviation> getGradeAbbreviations() {
		return gradeAbbreviations;
	}

	public void setGradeAbbreviations(List<ApiGradeAbbreviation> gradeAbbreviations) {
		this.gradeAbbreviations = gradeAbbreviations;
	}

	public List<ApiProcessingEvidenceType> getProcessingEvidenceTypes() {
		return processingEvidenceTypes;
	}

	public void setProcessingEvidenceTypes(List<ApiProcessingEvidenceType> processingEvidenceTypes) {
		this.processingEvidenceTypes = processingEvidenceTypes;
	}

	public List<ApiSemiProduct> getSemiProducts() {
		return semiProducts;
	}

	public void setSemiProducts(List<ApiSemiProduct> semiProducts) {
		this.semiProducts = semiProducts;
	}

}
