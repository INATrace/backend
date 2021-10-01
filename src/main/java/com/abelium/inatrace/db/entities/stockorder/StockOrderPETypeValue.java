package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.processingevidencefield.FileInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class StockOrderPETypeValue extends TimestampEntity {

	@Version
	private Long entityVersion;
	
	@Column
	private String identifier;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Boolean isRequired;

	@OneToOne
	private FileInfo fileInfo;
	
	@ManyToOne
	@NotNull
	private StockOrder stockOrder;

	@ManyToOne
	@NotNull
	private ProcessingEvidenceType processingEvidenceType;
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public ProcessingEvidenceType getProcessingEvidenceType() {
		return processingEvidenceType;
	}

	public void setProcessingEvidenceType(ProcessingEvidenceType processingEvidenceType) {
		this.processingEvidenceType = processingEvidenceType;
	}
	
}
