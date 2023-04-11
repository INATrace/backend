package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserCustomerPlantInformation extends BaseEntity {

	@ManyToOne
	private UserCustomer userCustomer;

	@ManyToOne(cascade = CascadeType.ALL)
	private PlantInformation plantInformation;

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}

	public PlantInformation getPlantInformation() {
		return plantInformation;
	}

	public void setPlantInformation(PlantInformation plantInformation) {
		this.plantInformation = plantInformation;
	}
}
