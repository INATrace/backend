package com.abelium.inatrace.components.processingaction.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;

import io.swagger.annotations.ApiModelProperty;

/**
 * Processing action API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiProcessingAction extends ApiBaseEntity {

	@ApiModelProperty(value = "Processing action name")
	private String name;
	
	@ApiModelProperty(value = "Processing action description")
	private String description;
	
	@ApiModelProperty(value = "Processing action language")
	private Language language;
	
	@ApiModelProperty(value = "Processing action prefix")
	private String prefix;

	@ApiModelProperty(value = "Processing action repacked outputs")
	private Boolean repackedOutputs;
	
	@ApiModelProperty(value = "Processing action maximum output weight")
	private BigDecimal maxOutputWeight;
	
	@ApiModelProperty(value = "Processing action public timeline label")
	private String publicTimelineLabel;
	
	@ApiModelProperty(value = "Processing action public timeline location")
	private String publicTimelineLocation;

	@ApiModelProperty(value = "Processing action company")
	private ApiCompanyBase company;
	
	@ApiModelProperty(value = "Processing action input semi product")
	private ApiSemiProduct inputSemiProduct;
	
	@ApiModelProperty(value = "Processing action input semi product")
	private ApiSemiProduct outputSemiProduct;
	
	@ApiModelProperty(value = "Processing action type")
	private ProcessingActionType type;

	@ApiModelProperty(value = "Processing action public timeline icon type")
	private PublicTimelineIconType publicTimelineIconType;
	
	@ApiModelProperty(value = "Processing action required document types")
	private List<ApiProcessingEvidenceType> requiredDocumentTypes = new ArrayList<>();

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
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Boolean getRepackedOutputs() {
		return repackedOutputs;
	}

	public void setRepackedOutputs(Boolean repackedOutputs) {
		this.repackedOutputs = repackedOutputs;
	}

	public BigDecimal getMaxOutputWeight() {
		return maxOutputWeight;
	}

	public void setMaxOutputWeight(BigDecimal maxOutputWeight) {
		this.maxOutputWeight = maxOutputWeight;
	}

	public String getPublicTimelineLabel() {
		return publicTimelineLabel;
	}

	public void setPublicTimelineLabel(String publicTimelineLabel) {
		this.publicTimelineLabel = publicTimelineLabel;
	}

	public String getPublicTimelineLocation() {
		return publicTimelineLocation;
	}

	public void setPublicTimelineLocation(String publicTimelineLocation) {
		this.publicTimelineLocation = publicTimelineLocation;
	}

	public ApiCompanyBase getCompany() {
		return company;
	}

	public void setCompany(ApiCompanyBase company) {
		this.company = company;
	}

	public ApiSemiProduct getInputSemiProduct() {
		return inputSemiProduct;
	}

	public void setInputSemiProduct(ApiSemiProduct inputSemiProduct) {
		this.inputSemiProduct = inputSemiProduct;
	}

	public ApiSemiProduct getOutputSemiProduct() {
		return outputSemiProduct;
	}

	public void setOutputSemiProduct(ApiSemiProduct outputSemiProduct) {
		this.outputSemiProduct = outputSemiProduct;
	}

	public ProcessingActionType getType() {
		return type;
	}

	public void setType(ProcessingActionType type) {
		this.type = type;
	}

	public PublicTimelineIconType getPublicTimelineIconType() {
		return publicTimelineIconType;
	}

	public void setPublicTimelineIconType(PublicTimelineIconType publicTimelineIconType) {
		this.publicTimelineIconType = publicTimelineIconType;
	}
	
	public List<ApiProcessingEvidenceType> getRequiredDocumentTypes() {
		return requiredDocumentTypes;
	}

	public void setRequiredDocumentTypes(List<ApiProcessingEvidenceType> requiredDocumentTypes) {
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

	public ApiProcessingAction() {
		super();
	}

	public ApiProcessingAction(String name, String description, Language language, String prefix, Boolean repackedOutputs,
			BigDecimal maxOutputWeight, String publicTimelineLabel, String publicTimelineLocation,
			ApiCompanyBase company, ApiSemiProduct inputSemiProduct, ApiSemiProduct outputSemiProduct,
			ProcessingActionType type, PublicTimelineIconType publicTimelineIconType,
			List<ApiProcessingEvidenceType> requiredDocumentTypes) {
		super();
		this.name = name;
		this.description = description;
		this.language = language;
		this.prefix = prefix;
		this.repackedOutputs = repackedOutputs;
		this.maxOutputWeight = maxOutputWeight;
		this.publicTimelineLabel = publicTimelineLabel;
		this.publicTimelineLocation = publicTimelineLocation;
		this.company = company;
		this.inputSemiProduct = inputSemiProduct;
		this.outputSemiProduct = outputSemiProduct;
		this.type = type;
		this.publicTimelineIconType = publicTimelineIconType;
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

}
