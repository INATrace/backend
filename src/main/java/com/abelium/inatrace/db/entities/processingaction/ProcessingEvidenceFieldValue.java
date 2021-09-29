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
	
}
