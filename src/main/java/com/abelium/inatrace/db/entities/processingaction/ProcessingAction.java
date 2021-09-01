package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.types.ProcessingActionType;
import com.abelium.inatrace.types.PublicTimelineIconType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(indexes = { @Index(columnList = "name") })
// TODO: NamedQueries
@NamedQueries({

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
	private Float maxOutputWeight;
	
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
	private PublicTimelineIconType publicTimelineIcon;
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL)
	private List<ProcessingActionProcessingEvidenceType> requiredDocumentTypes = new ArrayList<>();
	
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

	public Float getMaxOutputWeight() {
		return maxOutputWeight;
	}

	public void setMaxOutputWeight(Float maxOutputWeight) {
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

	public PublicTimelineIconType getPublicTimelineIcon() {
		return publicTimelineIcon;
	}

	public void setPublicTimelineIcon(PublicTimelineIconType publicTimelineIcon) {
		this.publicTimelineIcon = publicTimelineIcon;
	}

	public List<ProcessingActionProcessingEvidenceType> getRequiredDocumentTypes() {
		return requiredDocumentTypes;
	}

	public void setRequiredDocumentTypes(List<ProcessingActionProcessingEvidenceType> requiredDocumentTypes) {
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

	public ProcessingAction(String name, String description, String prefix, Boolean repackedOutputs,
			Float maxOutputWeight, Company company, SemiProduct inputSemiProduct, SemiProduct outputSemiProduct,
			String publicTimelineLabel, String publicTimelineLocation, ProcessingActionType type,
			PublicTimelineIconType publicTimelineIcon,
			List<ProcessingActionProcessingEvidenceType> requiredDocumentTypes) {
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
		this.publicTimelineIcon = publicTimelineIcon;
		this.requiredDocumentTypes = requiredDocumentTypes;
	}

	public ProcessingAction() {
		super();
	}
	
}
