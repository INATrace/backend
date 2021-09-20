package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.TranslatedEntity;
import com.abelium.inatrace.types.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(indexes = { @Index(columnList = "processingaction_id, language, name") })
public class ProcessingActionTranslation extends TranslatedEntity {

	@Version
	private Long entityVersion;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne
	@NotNull
	private ProcessingAction processingAction;

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
	
	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public ProcessingActionTranslation() {
		super();
	}

	public ProcessingActionTranslation(Language language) {
		super(language);
	}

	public ProcessingActionTranslation(String name, String description, @NotNull ProcessingAction processingAction) {
		super();
		this.name = name;
		this.description = description;
		this.processingAction = processingAction;
	}

}
