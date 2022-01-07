package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ComparisonOfPrice extends BaseEntity {

	/**
	 * prices
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "priceKey")
    @Column(name = "price")	
    @CollectionTable
    private Map<String, Double> prices = new HashMap<>();
	
	/**
	 * description
	 */
	@Lob
	private String description;
	
	public Map<String, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ComparisonOfPrice copy() {
		ComparisonOfPrice p = new ComparisonOfPrice();
		p.setPrices(new HashMap<>(getPrices()));
		p.setDescription(getDescription());
		return p;
	}
}

