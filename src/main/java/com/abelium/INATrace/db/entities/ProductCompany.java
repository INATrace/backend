package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.ProductCompanyType;

@Entity
public class ProductCompany extends BaseEntity {

	/** 
	 * product
	 */
	@ManyToOne
	private Product product;
	
	/**
	 * company
	 */
	@ManyToOne
	private Company company;
	
	/**
	 * type
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private ProductCompanyType type;


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ProductCompanyType getType() {
		return type;
	}

	public void setType(ProductCompanyType type) {
		this.type = type;
	}
}
