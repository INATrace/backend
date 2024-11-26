package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Entity representing connection between a Final product and selected supported Product Labels.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class FinalProductLabel extends BaseEntity {

	@ManyToOne
	@NotNull
	private FinalProduct finalProduct;

	@ManyToOne
	@NotNull
	private ProductLabel productLabel;

	public FinalProduct getFinalProduct() {
		return finalProduct;
	}

	public void setFinalProduct(FinalProduct finalProduct) {
		this.finalProduct = finalProduct;
	}

	public ProductLabel getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(ProductLabel productLabel) {
		this.productLabel = productLabel;
	}

}
