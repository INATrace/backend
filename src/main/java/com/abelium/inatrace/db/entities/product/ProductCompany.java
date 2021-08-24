package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.types.ProductCompanyType;

import javax.persistence.*;

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