package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class ProcessingEvidenceFieldValue extends TimestampEntity {

	@Version
	private Long entityVersion;
	
	@ManyToOne
	@NotNull
	private StockOrder stockOrder;

	@ManyToOne
	@NotNull
	private ProcessingEvidenceField processingEvidenceField;

	@Column
	private String stringValue;
	
	@Column
	private Float numericValue;
	
	@Column
	private Boolean booleanValue;
	
	@Column
	private Instant instantValue;

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public ProcessingEvidenceField getProcessingEvidenceField() {
		return processingEvidenceField;
	}

	public void setProcessingEvidenceField(ProcessingEvidenceField processingEvidenceField) {
		this.processingEvidenceField = processingEvidenceField;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Float getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(Float numericValue) {
		this.numericValue = numericValue;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public Instant getInstantValue() {
		return instantValue;
	}

	public void setInstantValue(Instant instantValue) {
		this.instantValue = instantValue;
	}
	
}
