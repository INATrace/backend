package com.abelium.INATrace.db.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import com.abelium.INATrace.db.base.BaseEntity;

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

