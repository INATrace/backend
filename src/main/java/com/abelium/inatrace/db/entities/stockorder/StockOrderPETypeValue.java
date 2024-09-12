package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceType;
import com.abelium.inatrace.db.entities.common.Document;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class StockOrderPETypeValue extends TimestampEntity {

	@Version
	private Long entityVersion;
	
	@Column
	private Instant date;

	@ManyToOne
	private Document document;

	@Column
	private Boolean otherEvidence;
	
	@ManyToOne
	@NotNull
	private StockOrder stockOrder;

	@ManyToOne
	@NotNull
	private ProcessingEvidenceType processingEvidenceType;

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Long getEntityVersion() {
		return entityVersion;
	}

	public void setEntityVersion(Long entityVersion) {
		this.entityVersion = entityVersion;
	}

	public Boolean getOtherEvidence() {
		return otherEvidence;
	}

	public void setOtherEvidence(Boolean otherEvidence) {
		this.otherEvidence = otherEvidence;
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
