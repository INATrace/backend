package com.abelium.inatrace.db.entities.process;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class ProcessStandard extends BaseEntity {
	
	/**
	 * Standard for this (product) process  
	 */
	@ManyToOne
	private Process process;
	
    /**
     * Document type (link, file)
     */
	@Column(length = Lengths.DEFAULT)
    private String type;
	
	/**
	 * description of this standard and certification
	 */
	@Lob
	private String description;
	
	/**
	 * certificate for this standard
	 */
	@ManyToOne
	private Document certificate;
	
	/**
	 * Validity
	 */
	@Column
	private LocalDate validity;
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Document getCertificate() {
		return certificate;
	}

	public void setCertificate(Document certificate) {
		this.certificate = certificate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}
	
	public ProcessStandard copy() {
		ProcessStandard s = new ProcessStandard();
		s.setDescription(getDescription());
		s.setCertificate(getCertificate());
		s.setType(getType());
		s.setValidity(getValidity());
		return s;
	}

}
