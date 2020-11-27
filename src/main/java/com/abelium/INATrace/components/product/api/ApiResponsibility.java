package com.abelium.INATrace.components.product.api;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import com.abelium.INATrace.api.types.Lengths;
import io.swagger.annotations.ApiModelProperty;

public class ApiResponsibility {
	
	@Length(max = 2000)
	@ApiModelProperty(value = "labor policies - Briefly describe labor policies you have in place in your company", position = 1)
	public String laborPolicies;

	@Length(max = 2000)
	@ApiModelProperty(value = "storage - Briefly describe your storage procedures", position = 2)
	public String relationship;
	
	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "farmers story - farmer or community", position = 3)
	public String farmer;

	@Length(max = 2000)
	@ApiModelProperty(value = "farmers story - text", position = 5)
	public String story;
	
	@ApiModelProperty(value = "farmers story - pictures", position = 6)
	@Valid
	public List<ApiResponsibilityFarmerPicture> pictures;

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

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public List<ApiResponsibilityFarmerPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<ApiResponsibilityFarmerPicture> pictures) {
		this.pictures = pictures;
	}
}
