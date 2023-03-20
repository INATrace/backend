package com.abelium.inatrace.components.value_chain.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.product.api.ApiProductType;
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

	@ApiModelProperty(value = "list of supported facility types", position = 5)
	private List<ApiFacilityType> facilityTypes;

	@ApiModelProperty(value = "list of supported measuring unit types", position = 6)
	private List<ApiMeasureUnitType> measureUnitTypes;

	@ApiModelProperty(value = "list of supported processing evidence types", position = 8)
	private List<ApiProcessingEvidenceType> processingEvidenceTypes;

	@ApiModelProperty(value = "list of supported processing evidence fields", position = 9)
	private List<ApiProcessingEvidenceField> processingEvidenceFields;

	@ApiModelProperty(value = "list of supported semi-products", position = 10)
	private List<ApiSemiProduct> semiProducts;

	@ApiModelProperty(value = "Selected product type", position = 11)
	private ApiProductType productType;

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

	public List<ApiProcessingEvidenceType> getProcessingEvidenceTypes() {
		return processingEvidenceTypes;
	}

	public void setProcessingEvidenceTypes(List<ApiProcessingEvidenceType> processingEvidenceTypes) {
		this.processingEvidenceTypes = processingEvidenceTypes;
	}

	public List<ApiProcessingEvidenceField> getProcessingEvidenceFields() {
		return processingEvidenceFields;
	}

	public void setProcessingEvidenceFields(List<ApiProcessingEvidenceField> processingEvidenceFields) {
		this.processingEvidenceFields = processingEvidenceFields;
	}

	public List<ApiSemiProduct> getSemiProducts() {
		return semiProducts;
	}

	public void setSemiProducts(List<ApiSemiProduct> semiProducts) {
		this.semiProducts = semiProducts;
	}

	public ApiProductType getProductType() {
		return productType;
	}

	public void setProductType(ApiProductType productType) {
		this.productType = productType;
	}
}
