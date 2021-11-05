package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ProductDataSharingAgreement extends BaseEntity {

	/**
	 * Product this document applies to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/**
	 * Description of this document
	 */
	@Column(length = Lengths.DEFAULT)
	private String description;

	/**
	 * Document attached to the product
	 */
	@ManyToOne
	private Document document;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
