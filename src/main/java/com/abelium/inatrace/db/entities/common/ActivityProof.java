package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.TimestampEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

import java.time.Instant;

/**
 * Activity proof entity which holds reference to document representing the proof of executing this activity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class ActivityProof extends TimestampEntity {

	@Column(updatable = false)
	private Instant formalCreationDate;

	@Column
	private Instant validUntil;

	@Column
	private String type;

	@ManyToOne
	private Document document;

	@Version
	private Long entityVersion;

	public Instant getFormalCreationDate() {
		return formalCreationDate;
	}

	public void setFormalCreationDate(Instant formalCreationDate) {
		this.formalCreationDate = formalCreationDate;
	}

	public Instant getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Instant validUntil) {
		this.validUntil = validUntil;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
