package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Entity holding relationship between product type and user customer (farmer).
 *
 * @author Borche Paspalovski, Sunesis d.o.o.
 */
@Entity
public class UserCustomerProductType extends BaseEntity {

	@ManyToOne
	private UserCustomer userCustomer;

	@ManyToOne
	private ProductType productType;

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
