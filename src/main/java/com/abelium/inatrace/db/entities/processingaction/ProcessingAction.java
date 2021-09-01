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
	
	@OneToMany(mappedBy = "processingAction", cascade = CascadeType.ALL) // Called as requiredDocTypes in nodeJS
	private List<ProcessingActionProcessingEvidenceType> requiredDocumentTypes = new ArrayList<>();
	
	// TODO: requiredFields?: FieldDefinition[] - many to many - API needed - 
	// Get better definition from Boris and Claudia. Keep it out for now.

	// TODO: add getters, setters and constructors after approval of this class
}
