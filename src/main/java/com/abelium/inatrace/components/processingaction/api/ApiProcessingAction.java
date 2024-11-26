package com.abelium.inatrace.components.processingaction.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingactiontranslation.api.ApiProcessingActionTranslation;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Processing action API model.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public class ApiProcessingAction extends ApiBaseEntity {

	@Schema(description = "Processing action name")
	private String name;
	
	@Schema(description = "Processing action description")
	private String description;
	
	@Schema(description = "Processing action language")
	private Language language;

	@Schema(description = "Sort order number. Lower number means first")
	private Long sortOrder;

	@Schema(description = "Processing action prefix")
	private String prefix;

	@Schema(description = "The estimated output quantity per unit for this processing action")
	private BigDecimal estimatedOutputQuantityPerUnit;
	
	@Schema(description = "Processing action public timeline label")
	private String publicTimelineLabel;
	
	@Schema(description = "Processing action public timeline location")
	private String publicTimelineLocation;

	@Schema(description = "Processing action company")
	private ApiCompanyBase company;
	
	@Schema(description = "Processing action input semi product")
	private ApiSemiProduct inputSemiProduct;

	@Schema(description = "List of supported output semi-products")
	private List<ApiProcessingActionOutputSemiProduct> outputSemiProducts;

	@Schema(description = "The input Final product")
	private ApiFinalProduct inputFinalProduct;

	@Schema(description = "The output final product")
	private ApiFinalProduct outputFinalProduct;

	@Schema(description = "Processing action repacked outputs when using output final product")
	private Boolean repackedOutputFinalProducts;

	@Schema(description = "Processing action maximum output weight when repacked outputs for final product is set to 'true'")
	private BigDecimal maxOutputWeight;

	@Schema(description = "The Final product for which the QR code tag will be generated (used with action type GENERATE_QR_CODE)")
	private ApiFinalProduct qrCodeForFinalProduct;
	
	@Schema(description = "Processing action type")
	private ProcessingActionType type;

	@Schema(description = "Processing action public timeline icon type")
	private PublicTimelineIconType publicTimelineIconType;

	@Schema(description = "Denoting if this processing action is Transfer or Quote of a Final product")
	private Boolean finalProductAction;

	@Schema(description = "List of value chains for this processing action")
	private List<ApiValueChain> valueChains;

	@Schema(description = "Processing action required document types")
	private List<ApiProcessingEvidenceType> requiredDocumentTypes = new ArrayList<>();
	
	@Schema(description = "Processing action required evidence fields")
	private List<ApiProcessingEvidenceField> requiredEvidenceFields = new ArrayList<>();

	@Schema(description = "List of facilities where this processing starts")
	private List<ApiFacility> supportedFacilities = new ArrayList<>();

	@Schema(description = "Processing action translations")
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

	public Boolean getRepackedOutputFinalProducts() {
		return repackedOutputFinalProducts;
	}

	public void setRepackedOutputFinalProducts(Boolean repackedOutputFinalProducts) {
		this.repackedOutputFinalProducts = repackedOutputFinalProducts;
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

	public List<ApiProcessingActionOutputSemiProduct> getOutputSemiProducts() {
		return outputSemiProducts;
	}

	public void setOutputSemiProducts(List<ApiProcessingActionOutputSemiProduct> outputSemiProducts) {
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

	public List<ApiValueChain> getValueChains() {
		return valueChains;
	}

	public void setValueChains(List<ApiValueChain> valueChains) {
		this.valueChains = valueChains;
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
