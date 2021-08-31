package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = { @Index(columnList = "name") })
// TODO: NamedQueries
@NamedQueries({

})
public class ProcessingAction extends TimestampEntity {

	@Column
	private Long entityVersion;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Boolean repackedOutputs;

	@Column
	private Float maxOutputWeight;
	
	@Column
	private String prefix;
	
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
	
	// TODO: requiredFields?: FieldDefinition[] - seems quite a mess in nodejs
	
	// TODO: are requireddoctypesidwithrequired? - seems to be a class with 4 properties
	
	// TODO: I assume next properties are enums?
//	@OneToOne
//	private PublicTimelineIconType publicTimelineIconType; SHIP, LEAF, WAREHOUSE, QRCODE, OTHER
	
//	@OneToOne
//	private ProcessingActionType processingActionType; PROCESSING, SHIPMENT, “TRANSFER
	
// 	I assume this is a type since it extends from codebook, how do you insert the types on the DB?
//	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL)
//	private List<ProcessingEvidenceType> processingEvidenceTypes = new ArrayList<>();
	
}
