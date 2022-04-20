package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.entities.company.Company;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ProductLabelContent extends ProductContent {
	
	@Version
	private long entityVersion;
	
	/**
	 * company - owner - for view only
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * origin - farmer location - Use the pins on the map to mark locations of farmers or suppliers and 
	 * indicate the number of farmers or suppliers you work with directly or indirectly at each location. 
	 */
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductLocation> originLocations = new ArrayList<>(0);
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<ProductLocation> getOriginLocations() {
		return originLocations;
	}

	public void setOriginLocations(List<ProductLocation> originLocations) {
		this.originLocations = originLocations;
	}

	// Create a copy for persisting to db
	public static ProductLabelContent fromProduct(Product p) {
		ProductLabelContent plc = new ProductLabelContent();
		plc.setName(p.getName());
		plc.setPhoto(p.getPhoto());
		plc.setDescription(p.getDescription());
		plc.setIngredients(p.getIngredients());
		plc.setNutritionalValue(p.getNutritionalValue());
		plc.setHowToUse(p.getHowToUse());
		plc.setOriginText(p.getOriginText());
		plc.setKeyMarketsShare(new HashMap<>(p.getKeyMarketsShare()));
		plc.setProcess(p.getProcess().copy());
		plc.setResponsibility(p.getResponsibility().copy());
		plc.setSustainability(p.getSustainability().copy());
		plc.setSpecialityDocument(p.getSpecialityDocument());
		plc.setSpecialityDescription(p.getSpecialityDescription());
		plc.setSettings(p.getSettings().copy());
		plc.setComparisonOfPrice(p.getComparisonOfPrice().copy());
		plc.setCompany(p.getCompany()); // no copy -- read only!
		plc.setOriginLocations(p.getOriginLocations().stream().map(ProductLocation::copy).collect(Collectors.toList()));
        plc.setJourney(p.getJourney().copy());
		return plc;
	}
}
