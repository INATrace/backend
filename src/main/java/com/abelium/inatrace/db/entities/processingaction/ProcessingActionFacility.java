package com.abelium.inatrace.db.entities.processingaction;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.facility.Facility;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class ProcessingActionFacility extends BaseEntity {

	@ManyToOne
	@NotNull
	private ProcessingAction processingAction;

	@ManyToOne
	@NotNull
	private Facility facility;

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

}
