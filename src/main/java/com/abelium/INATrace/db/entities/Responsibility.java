package com.abelium.INATrace.db.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

@Entity
public class Responsibility extends BaseEntity {

	/**
	 * labor policies - Briefly describe labor policies you have in place in your company and 
	 * activities you pursue to make them happen (example: women inclusion, no child labor, etc.)
	 */
	@Lob
	private String laborPolicies;

	/**
	 * relationship with farmers - Describe your working relationship with your farmers and suppliers. 
	 * Do you support farmers’ livelihoods in any special way (e.g. trainings, Inputs, agriculture 
	 * extensions, education for their kids, employment of women etc.)?
	 */
	@Lob
	private String relationship;
	
	/**
	 * farmers story - farmer or community
	 */
	@Column(length = Lengths.DEFAULT)
	private String farmer;
	
	/**
	 * farmers story - pictures
	 */
	@OneToMany(mappedBy = "responsibility", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ResponsibilityFarmerPicture> pictures = new ArrayList<>();
	
	/**
	 * farmers story - text
	 */
	@Lob
	private String story;

	
	public String getLaborPolicies() {
		return laborPolicies;
	}

	public void setLaborPolicies(String laborPolicies) {
		this.laborPolicies = laborPolicies;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFarmer() {
		return farmer;
	}

	public void setFarmer(String farmer) {
		this.farmer = farmer;
	}
	
	public List<ResponsibilityFarmerPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<ResponsibilityFarmerPicture> pictures) {
		this.pictures = pictures;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}
	
	public Responsibility copy() {
		Responsibility r = new Responsibility();
		r.setRelationship(getRelationship());
		r.setFarmer(getFarmer());
		r.setPictures(getPictures().stream().map(p -> p.copy()).collect(Collectors.toList()));
		r.setStory(getStory());
		return r;
	}
}
