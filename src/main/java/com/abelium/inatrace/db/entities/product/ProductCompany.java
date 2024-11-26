package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.types.ProductCompanyType;
import jakarta.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "ProductCompany.getCompanyProductsAsBuyerOrExporter",
		            query = "SELECT pc FROM ProductCompany pc WHERE pc.company.id = :companyId AND pc.type IN (com.abelium.inatrace.types.ProductCompanyType.EXPORTER, com.abelium.inatrace.types.ProductCompanyType.BUYER)"),
		@NamedQuery(name = "ProductCompany.getProductCompaniesByAssociationType",
		            query = "SELECT DISTINCT pc.company FROM ProductCompany pc WHERE pc.product.id IN :productIds AND pc.type = :associationType AND pc.company.id <> :companyId"),
		@NamedQuery(name = "ProductCompany.getCompanyProductsWithAnyRole",
				    query = "SELECT DISTINCT pc.product FROM ProductCompany pc WHERE pc.company.id = :companyId"),
		@NamedQuery(name = "ProductCompany.getProductOwnerCompanies",
				    query = "SELECT pc.company FROM ProductCompany pc where pc.product.id = :productId AND pc.type = com.abelium.inatrace.types.ProductCompanyType.OWNER")
})
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
