package com.abelium.inatrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;

@Entity
public class ResponsibilityFarmerPicture extends BaseEntity {

	/**
	 * Photo  is for this (product) responsibility  
	 */
	@ManyToOne
	private Responsibility responsibility;
	
	/**
	 * Description of this document
	 */
	@Column(length = Lengths.DEFAULT)
	private String description;

	/**
	 * Photo (of farmer) attached to the responsibility
	 */
	@ManyToOne
	private Document document;


	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
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
	
	public ResponsibilityFarmerPicture copy() {
		ResponsibilityFarmerPicture p = new ResponsibilityFarmerPicture();
		p.setDescription(getDescription());
		p.setDocument(getDocument());
		return p;
	}
}
