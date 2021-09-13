package com.abelium.inatrace.db.entities.processingevidencefield;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.value_chain.ValueChainProcessingEvidenceField;
import com.abelium.inatrace.types.ProcessingEvidenceFieldType;

@Entity
@Table(indexes = { @Index(columnList = "label") })
@NamedQueries({
	@NamedQuery(name = "ProcessingEvidenceField.listProcessingEvidenceFieldsByValueChain", 
				query = "SELECT pef FROM ProcessingEvidenceField pef "
						+ "INNER JOIN pef.valueChains vcs "
						+ "INNER JOIN vcs.valueChain vc "
						+ "WHERE vc.id = :valueChainId"),
	@NamedQuery(name = "ProcessingEvidenceField.countProcessingEvidenceFieldsByValueChain",
	            query = "SELECT COUNT(pef) FROM ProcessingEvidenceField pef "
						+ "INNER JOIN pef.valueChains vcs "
						+ "INNER JOIN vcs.valueChain vc "
						+ "WHERE vc.id = :valueChainId")
})
public class ProcessingEvidenceField extends TimestampEntity {

	@Version
	private Long entityVersion;

	@Column
	private String label;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM, nullable = false)
	private ProcessingEvidenceFieldType type = ProcessingEvidenceFieldType.STRING;
	
	@Column
	private Boolean required;
	
	@Column
	private Boolean mandatory;
	
	@Column
	private Boolean requiredOnQuote;
	
	@Column
	private String stringValue;
	
	@Column
	private Integer numericValue;
	
	// TODO: Not possible to map ab object column, any ideas?
//	@Column
//	private Object objectValue;
	
	@Column
	private Integer fileMultiplicity;
	
	@OneToMany(mappedBy = "processingEvidenceField", cascade = CascadeType.ALL)
	private List<FileInfo> files = new ArrayList<>();
	
	@OneToMany(mappedBy = "processingEvidenceField", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ValueChainProcessingEvidenceField> valueChains;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ProcessingEvidenceFieldType getType() {
		return type;
	}

	public void setType(ProcessingEvidenceFieldType type) {
		this.type = type;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getRequiredOnQuote() {
		return requiredOnQuote;
	}

	public void setRequiredOnQuote(Boolean requiredOnQuote) {
		this.requiredOnQuote = requiredOnQuote;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(Integer numericValue) {
		this.numericValue = numericValue;
	}

//	public Object getObjectValue() {
//		return objectValue;
//	}
//
//	public void setObjectValue(Object objectValue) {
//		this.objectValue = objectValue;
//	}

	public Integer getFileMultiplicity() {
		return fileMultiplicity;
	}

	public void setFileMultiplicity(Integer fileMultiplicity) {
		this.fileMultiplicity = fileMultiplicity;
	}

	public List<FileInfo> getFiles() {
		return files;
	}

	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

	public ProcessingEvidenceField(String label, ProcessingEvidenceFieldType type, Boolean required, Boolean mandatory,
			Boolean requiredOnQuote, Integer numericValue, String stringValue,
			Integer fileMultiplicity, List<FileInfo> files) {
		super();
		this.label = label;
		this.type = type;
		this.required = required;
		this.mandatory = mandatory;
		this.requiredOnQuote = requiredOnQuote;
		this.stringValue = stringValue;
		this.numericValue = numericValue;
//		this.objectValue = objectValue;
		this.fileMultiplicity = fileMultiplicity;
		this.files = files;
	}

	public ProcessingEvidenceField() {
		super();
	}
	
}
