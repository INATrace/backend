package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

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
