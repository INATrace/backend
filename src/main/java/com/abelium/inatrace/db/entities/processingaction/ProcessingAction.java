package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = { @Index(columnList = "name") })
@NamedQueries({
	@NamedQuery(name = "ProcessingAction.listProcessingActionsByCompany", 
			query = "SELECT pa FROM ProcessingAction pa "
					+ "INNER JOIN pa.company c "
					+ "WHERE c.id = :companyId"),
	@NamedQuery(name = "ProcessingAction.countProcessingActionsByCompany",
			query = "SELECT COUNT(pa) FROM ProcessingAction pa "
					+ "WHERE pa.company.id = :companyId"),
})
public class ProcessingAction extends TimestampEntity {

	@Version
	private Long entityVersion;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String prefix;
	
	@Column
	private Boolean repackedOutputs;

	@Column
	private BigDecimal maxOutputWeight;
	
	@ManyToOne
	private Company company;
	
	@OneToOne
	private SemiProduct inputSemiProduct;
	
	@OneToOne
	private SemiProduct outputSemiProduct;
	
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
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessingActionPET> requiredDocumentTypes = new ArrayList<>();
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessingActionPEF> processingEvidenceFields = new ArrayList<>();
	
	// TODO: requiredFields?: FieldDefinition[] - many to many - API needed - 
	// Get better definition from Boris and Claudia. Keep it out for now.

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

	public SemiProduct getOutputSemiProduct() {
		return outputSemiProduct;
	}

	public void setOutputSemiProduct(SemiProduct outputSemiProduct) {
		this.outputSemiProduct = outputSemiProduct;
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

	public List<ProcessingActionPET> getRequiredDocumentTypes() {
		return requiredDocumentTypes;
	}

	public void setRequiredDocumentTypes(List<ProcessingActionPET> requiredDocumentTypes) {
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

	public ProcessingAction(String name, String description, String prefix, Boolean repackedOutputs,
			BigDecimal maxOutputWeight, Company company, SemiProduct inputSemiProduct, SemiProduct outputSemiProduct,
			String publicTimelineLabel, String publicTimelineLocation, ProcessingActionType type,
			PublicTimelineIconType publicTimelineIcon,
			List<ProcessingActionPET> requiredDocumentTypes) {
		super();
		this.name = name;
		this.description = description;
		this.prefix = prefix;
		this.repackedOutputs = repackedOutputs;
		this.maxOutputWeight = maxOutputWeight;
		this.company = company;
		this.inputSemiProduct = inputSemiProduct;
		this.outputSemiProduct = outputSemiProduct;
		this.publicTimelineLabel = publicTimelineLabel;
		this.publicTimelineLocation = publicTimelineLocation;
		this.type = type;
		this.publicTimelineIconType = publicTimelineIcon;
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

	public ProcessingAction() {
		super();
	}
	
}
