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
@Table
@NamedQueries({
	@NamedQuery(name = "ProcessingAction.listProcessingActionsByCompany", 
			query = "SELECT pa FROM ProcessingAction pa "
					+ "INNER JOIN FETCH pa.processingActionTranslations t "
					+ "INNER JOIN pa.company c "
					+ "WHERE c.id = :companyId "
					+ "AND t.language = :language"),
	@NamedQuery(name = "ProcessingAction.countProcessingActionsByCompany",
			query = "SELECT COUNT(pa) FROM ProcessingAction pa "
					+ "INNER JOIN pa.processingActionTranslations t "
					+ "WHERE pa.company.id = :companyId "
					+ "AND t.language = :language"),
	@NamedQuery(name = "ProcessingAction.listProcessingActions", 
			query = "SELECT pa FROM ProcessingAction pa "
					+ "INNER JOIN FETCH pa.processingActionTranslations t "
					+ "WHERE t.language = :language"),
	@NamedQuery(name = "ProcessingAction.countProcessingActions",
			query = "SELECT COUNT(pa) FROM ProcessingAction pa "
					+ "INNER JOIN pa.processingActionTranslations t "
					+ "WHERE t.language = :language")
})
public class ProcessingAction extends TimestampEntity {

	@Version
	private Long entityVersion;

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
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessingActionTranslation> processingActionTranslations = new ArrayList<>();

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
	
	public List<ProcessingActionPEF> getProcessingEvidenceFields() {
		return processingEvidenceFields;
	}

	public void setProcessingEvidenceFields(List<ProcessingActionPEF> processingEvidenceFields) {
		this.processingEvidenceFields = processingEvidenceFields;
	}
	
	public List<ProcessingActionTranslation> getProcessingActionTranslations() {
		return processingActionTranslations;
	}

	public void setProcessingActionTranslations(List<ProcessingActionTranslation> processingActionTranslations) {
		this.processingActionTranslations = processingActionTranslations;
	}

	public ProcessingAction(String prefix, Boolean repackedOutputs, BigDecimal maxOutputWeight, Company company,
			SemiProduct inputSemiProduct, SemiProduct outputSemiProduct, String publicTimelineLabel,
			String publicTimelineLocation, ProcessingActionType type, PublicTimelineIconType publicTimelineIconType,
			List<ProcessingActionPET> requiredDocumentTypes, List<ProcessingActionPEF> processingEvidenceFields,
			List<ProcessingActionTranslation> processingActionTranslations) {
		super();
		this.prefix = prefix;
		this.repackedOutputs = repackedOutputs;
		this.maxOutputWeight = maxOutputWeight;
		this.company = company;
		this.inputSemiProduct = inputSemiProduct;
		this.outputSemiProduct = outputSemiProduct;
		this.publicTimelineLabel = publicTimelineLabel;
		this.publicTimelineLocation = publicTimelineLocation;
		this.type = type;
		this.publicTimelineIconType = publicTimelineIconType;
		this.requiredDocumentTypes = requiredDocumentTypes;
		this.processingEvidenceFields = processingEvidenceFields;
		this.processingActionTranslations = processingActionTranslations;
	}

	public ProcessingAction() {
		super();
	}
	
}
