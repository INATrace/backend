package com.abelium.inatrace.db.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProductLocation extends Location {
	
	/**
	 * Product at this location
	 */
	@ManyToOne
	private Product product;

	/**
	 * Product at this location
	 */
	@ManyToOne
	private ProductLabelContent content;
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductContent getContent() {
		return content;
	}

	public void setContent(ProductLabelContent content) {
		this.content = content;
	}

	public ProductLocation copy() {
		ProductLocation l = new ProductLocation();
		l.setAddress(getAddress());
		l.setLatitude(getLatitude());
		l.setLongitude(getLongitude());
		l.setNumberOfFarmers(getNumberOfFarmers());
		l.setPinName(getPinName());
		return null;
	}
}
