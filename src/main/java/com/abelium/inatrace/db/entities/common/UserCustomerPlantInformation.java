package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// FIXME: delete this entity after migration
@Entity
public class UserCustomerPlantInformation extends BaseEntity {

	@ManyToOne
	private UserCustomer userCustomer;

	@ManyToOne(cascade = CascadeType.ALL)
	private FarmPlantInformation plantInformation;

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}

	public FarmPlantInformation getPlantInformation() {
		return plantInformation;
	}

	public void setPlantInformation(FarmPlantInformation plantInformation) {
		this.plantInformation = plantInformation;
	}
}
