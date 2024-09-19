package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.product.FinalProduct;
import com.abelium.inatrace.db.entities.value_chain.ProcessingActionValueChain;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "ProcessingAction.countProcessingActions",
				query = "SELECT COUNT(pa) FROM ProcessingAction pa INNER JOIN pa.processingActionTranslations t WHERE t.language = :language")
})
public class ProcessingAction extends TimestampEntity {

	@Version
	private Long entityVersion;

	@Column
	private Long sortOrder;

	@Column
	private String prefix;

	@Column
	private BigDecimal estimatedOutputQuantityPerUnit;
	
	@ManyToOne
	private Company company;

	/**
	 * Used when we have action types: PROCESSING, QUOTE, TRANSFER, FINAL_PROCESSING, GENERATE_QR_CODE
	 */
	@ManyToOne
	private SemiProduct inputSemiProduct;

	/**
	 * Used when we have action types: PROCESSING, QUOTE, TRANSFER, GENERATE_QR_CODE.
	 * If type is PROCESSING, we can have one or more output semi-products. In the other case only one output semi-product is allowed.
	 */
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionOutputSemiProduct> outputSemiProducts = new HashSet<>();

	/**
	 * Used when we have action types QUOTE or TRANSFER and finalProductAction value to true
	 */
	@ManyToOne
	private FinalProduct inputFinalProduct;

	/**
	 * Used when we have action type FINAL_PROCESSING or action types QUOTE and TRANSFER with finalProductAction value to true
	 */
	@ManyToOne
	private FinalProduct outputFinalProduct;

	/**
	 * Specified if the output of the final product should be repacked. It's used only when 'outputFinalProduct' is set.
	 */
	@Column
	private Boolean repackedOutputFinalProducts;

	/**
	 * Specifies the maximum output weight when using 'repackedOutputFinalProducts' (set value to 'true').
	 */
	@Column
	private BigDecimal maxOutputWeight;

	/**
	 * Used when we have action type GENERATE_QR_CODE. It holds the reference to the Final product
	 * that will be tagged by the generated QR code tag. This is used to connect the QR code tag with the Final product QR labels.
	 */
	@ManyToOne
	private FinalProduct qrCodeForFinalProduct;
	
	@Column
	private String publicTimelineLabel;
	
	@Column
	private String publicTimelineLocation;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private ProcessingActionType type;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PublicTimelineIconType publicTimelineIconType;

	/**
	 * Used to set if we are working with transfer or quote action for final products (from final product to final product)
	 */
	@Column
	private Boolean finalProductAction;

	/**
	 * The value chains that this Processing action supports - used to source semi-products, proc. evidence types and proc. evidence fields
	 */
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionValueChain> processingActionsValueChains = new HashSet<>();
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionPET> requiredDocumentTypes = new HashSet<>();
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionPEF> processingEvidenceFields = new HashSet<>();

	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionFacility> processingActionFacilities = new HashSet<>();
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProcessingActionTranslation> processingActionTranslations = new HashSet<>();

	public ProcessingAction() {
		super();
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SemiProduct getInputSemiProduct() {
		return inputSemiProduct;
	}

	public void setInputSemiProduct(SemiProduct inputSemiProduct) {
		this.inputSemiProduct = inputSemiProduct;
	}

	public Set<ProcessingActionOutputSemiProduct> getOutputSemiProducts() {
		return outputSemiProducts;
	}

	public void setOutputSemiProducts(Set<ProcessingActionOutputSemiProduct> outputSemiProducts) {
		this.outputSemiProducts = outputSemiProducts;
	}

	public FinalProduct getInputFinalProduct() {
		return inputFinalProduct;
	}

	public void setInputFinalProduct(FinalProduct inputFinalProduct) {
		this.inputFinalProduct = inputFinalProduct;
	}

	public FinalProduct getOutputFinalProduct() {
		return outputFinalProduct;
	}

	public void setOutputFinalProduct(FinalProduct outputFinalProduct) {
		this.outputFinalProduct = outputFinalProduct;
	}

	public FinalProduct getQrCodeForFinalProduct() {
		return qrCodeForFinalProduct;
	}

	public void setQrCodeForFinalProduct(FinalProduct qrCodeForFinalProduct) {
		this.qrCodeForFinalProduct = qrCodeForFinalProduct;
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

	public Set<ProcessingActionValueChain> getProcessingActionsValueChains() {
		if (processingActionsValueChains == null) {
			processingActionsValueChains = new HashSet<>();
		}
		return processingActionsValueChains;
	}

	public void setProcessingActionsValueChains(Set<ProcessingActionValueChain> processingActionsValueChains) {
		this.processingActionsValueChains = processingActionsValueChains;
	}

	public Set<ProcessingActionPET> getRequiredDocumentTypes() {
		return requiredDocumentTypes;
	}

	public void setRequiredDocumentTypes(Set<ProcessingActionPET> requiredDocumentTypes) {
		this.requiredDocumentTypes = requiredDocumentTypes;
	}
	
	public Set<ProcessingActionPEF> getProcessingEvidenceFields() {
		return processingEvidenceFields;
	}

	public void setProcessingEvidenceFields(Set<ProcessingActionPEF> processingEvidenceFields) {
		this.processingEvidenceFields = processingEvidenceFields;
	}

	public Set<ProcessingActionFacility> getProcessingActionFacilities() {
		return processingActionFacilities;
	}

	public void setProcessingActionFacilities(Set<ProcessingActionFacility> processingActionFacilities) {
		this.processingActionFacilities = processingActionFacilities;
	}

	public Set<ProcessingActionTranslation> getProcessingActionTranslations() {
		return processingActionTranslations;
	}

	public void setProcessingActionTranslations(Set<ProcessingActionTranslation> processingActionTranslations) {
		this.processingActionTranslations = processingActionTranslations;
	}

}
