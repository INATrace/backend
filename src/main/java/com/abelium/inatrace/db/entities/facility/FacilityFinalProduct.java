package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.product.FinalProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Entity that holds the connecting relationship between Facility and FinalProduct.
 */
@Entity
public class FacilityFinalProduct extends BaseEntity {

	@NotNull
	@ManyToOne(optional = false)
	private Facility facility;

	@NotNull
	@ManyToOne(optional = false)
	private FinalProduct finalProduct;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public FinalProduct getFinalProduct() {
		return finalProduct;
	}

	public void setFinalProduct(FinalProduct finalProduct) {
		this.finalProduct = finalProduct;
	}

}

