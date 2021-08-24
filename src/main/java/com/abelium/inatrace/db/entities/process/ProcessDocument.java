package com.abelium.inatrace.db.entities.process;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProcessDocument extends BaseEntity {

	/**
	 * Document is for this (product) process  
	 */
	@ManyToOne
	private Process process;
	
	/**
	 * Description of this document
	 */
	@Column(length = Lengths.DEFAULT)
	private String description;

	/**
	 * Document attached to the process
	 */
	@ManyToOne
	private Document document;

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

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	public ProcessDocument copy() {
		ProcessDocument d = new ProcessDocument();
		d.setDescription(getDescription());
		d.setDocument(getDocument());
		return d;
	}
}