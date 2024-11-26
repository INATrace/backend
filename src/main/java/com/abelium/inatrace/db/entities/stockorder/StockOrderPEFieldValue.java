package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ProcessingEvidenceField;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class StockOrderPEFieldValue extends TimestampEntity {

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
	private BigDecimal numericValue;
	
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

	public BigDecimal getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(BigDecimal numericValue) {
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
