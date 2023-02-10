package com.abelium.inatrace.components.processingaction.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingactiontranslation.api.ApiProcessingActionTranslation;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

	@ApiModelProperty(value = "Sort order number. Lower number means first")
	private Long sortOrder;

	@ApiModelProperty(value = "Processing action prefix")
	private String prefix;

	@ApiModelProperty(value = "Processing action repacked outputs")
	private Boolean repackedOutputs;
	
	@ApiModelProperty(value = "Processing action maximum output weight")
	private BigDecimal maxOutputWeight;

	@ApiModelProperty(value = "The estimated output quantity per unit for this processing action")
	private BigDecimal estimatedOutputQuantityPerUnit;
	
	@ApiModelProperty(value = "Processing action public timeline label")
	private String publicTimelineLabel;
	
	@ApiModelProperty(value = "Processing action public timeline location")
	private String publicTimelineLocation;

	@ApiModelProperty(value = "Processing action company")
	private ApiCompanyBase company;
	
	@ApiModelProperty(value = "Processing action input semi product")
	private ApiSemiProduct inputSemiProduct;

	@ApiModelProperty(value = "List of supported output semi-products")
	private List<ApiSemiProduct> outputSemiProducts;

	@ApiModelProperty(value = "The input Final product")
	private ApiFinalProduct inputFinalProduct;

	@ApiModelProperty(value = "The output final product")
	private ApiFinalProduct outputFinalProduct;

	@ApiModelProperty(value = "The Final product for which the QR code tag will be generated (used with action type GENERATE_QR_CODE)")
	private ApiFinalProduct qrCodeForFinalProduct;
	
	@ApiModelProperty(value = "Processing action type")
	private ProcessingActionType type;

	@ApiModelProperty(value = "Processing action public timeline icon type")
	private PublicTimelineIconType publicTimelineIconType;

	@ApiModelProperty(value = "Denoting if this processing action is Transfer or Quote of a Final product")
	private Boolean finalProductAction;

	@ApiModelProperty(value = "The value chain that this Processing action supports")
	private ApiValueChain valueChain;
	
	@ApiModelProperty(value = "Processing action required document types")
	private List<ApiProcessingEvidenceType> requiredDocumentTypes = new ArrayList<>();
	
	@ApiModelProperty(value = "Processing action required evidence fields")
	private List<ApiProcessingEvidenceField> requiredEvidenceFields = new ArrayList<>();

	@ApiModelProperty(value = "List of facilities where this processing starts")
	private List<ApiFacility> supportedFacilities = new ArrayList<>();

	@ApiModelProperty(value = "Processing action translations")
	private List<ApiProcessingActionTranslation> translations = new ArrayList<>();

	public ApiProcessingAction() {
		super();
	}

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

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
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

	public BigDecimal getEstimatedOutputQuantityPerUnit() {
		return estimatedOutputQuantityPerUnit;
	}

	public void setEstimatedOutputQuantityPerUnit(BigDecimal estimatedOutputQuantityPerUnit) {
		this.estimatedOutputQuantityPerUnit = estimatedOutputQuantityPerUnit;
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

	public List<ApiSemiProduct> getOutputSemiProducts() {
		return outputSemiProducts;
	}

	public void setOutputSemiProducts(List<ApiSemiProduct> outputSemiProducts) {
		this.outputSemiProducts = outputSemiProducts;
	}

	public ApiFinalProduct getInputFinalProduct() {
		return inputFinalProduct;
	}

	public void setInputFinalProduct(ApiFinalProduct inputFinalProduct) {
		this.inputFinalProduct = inputFinalProduct;
	}

	public ApiFinalProduct getOutputFinalProduct() {
		return outputFinalProduct;
	}

	public void setOutputFinalProduct(ApiFinalProduct outputFinalProduct) {
		this.outputFinalProduct = outputFinalProduct;
	}

	public ApiFinalProduct getQrCodeForFinalProduct() {
		return qrCodeForFinalProduct;
	}

	public void setQrCodeForFinalProduct(ApiFinalProduct qrCodeForFinalProduct) {
		this.qrCodeForFinalProduct = qrCodeForFinalProduct;
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

	public Boolean getFinalProductAction() {
		return finalProductAction;
	}

	public void setFinalProductAction(Boolean finalProductAction) {
		this.finalProductAction = finalProductAction;
	}

	public ApiValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ApiValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public List<ApiProcessingEvidenceType> getRequiredDocumentTypes() {
		return requiredDocumentTypes;
	}

	public void setRequiredDocumentTypes(List<ApiProcessingEvidenceType> requiredDocumentTypes) {
		this.requiredDocumentTypes = requiredDocumentTypes;
	}
	
	public List<ApiProcessingEvidenceField> getRequiredEvidenceFields() {
		return requiredEvidenceFields;
	}

	public void setRequiredEvidenceFields(List<ApiProcessingEvidenceField> requiredEvidenceFields) {
		this.requiredEvidenceFields = requiredEvidenceFields;
	}

	public List<ApiFacility> getSupportedFacilities() {
		return supportedFacilities;
	}

	public void setSupportedFacilities(List<ApiFacility> supportedFacilities) {
		this.supportedFacilities = supportedFacilities;
	}

	public List<ApiProcessingActionTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<ApiProcessingActionTranslation> translations) {
		this.translations = translations;
	}

}
