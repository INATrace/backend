package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.Document;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Certification extends TimestampEntity {
	
	@Version
	private Long entityVersion;
	
	@Column
	private Document certificate;
	
	@Column
	private String description;
	
	@Column
	private String type;
	
	@Column
	private Instant validity;
	
	@ManyToOne
	private StockOrder stockOrder;

	public Document getCertificate() {
		return certificate;
	}

	public void setCertificate(Document certificate) {
		this.certificate = certificate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Instant getValidity() {
		return validity;
	}

	public void setValidity(Instant validity) {
		this.validity = validity;
	}

	public Certification(Document certificate, String description, String type, Instant validity) {
		super();
		this.certificate = certificate;
		this.description = description;
		this.type = type;
		this.validity = validity;
	}

	public Certification() {
		super();
	}
	
}
