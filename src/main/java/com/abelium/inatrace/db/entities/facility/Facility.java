package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "Facility.listFacilitiesByCompany", query = "SELECT f FROM Facility f INNER JOIN FETCH f.facilityTranslations t INNER JOIN f.company c WHERE c.id = :companyId AND t.language = :language"),
	@NamedQuery(name = "Facility.countFacilitiesByCompany", query = "SELECT COUNT(f) FROM Facility f WHERE f.company.id = :companyId"),

	@NamedQuery(name = "Facility.listCollectingFacilitiesByCompany",
				query = "SELECT f FROM Facility f INNER JOIN FETCH f.facilityTranslations t INNER JOIN f.company c WHERE c.id = :companyId AND f.isCollectionFacility = true AND t.language = :language"),
	@NamedQuery(name = "Facility.listSellingFacilitiesByCompany",
				query = "SELECT f FROM Facility f INNER JOIN FETCH f.facilityTranslations t INNER JOIN f.company c WHERE c.id = :companyId AND f.isPublic = true AND t.language = :language"),

	@NamedQuery(name = "Facility.countCollectingFacilitiesByCompany", query = "SELECT COUNT(f) FROM Facility f WHERE f.company.id = :companyId AND f.isCollectionFacility = true"),
	@NamedQuery(name = "Facility.countSellingFacilitiesByCompany", query = "SELECT COUNT(f) FROM Facility f WHERE f.company.id = :companyId AND f.isPublic = true")
})
public class Facility extends TimestampEntity {

	@Version
	private Long entityVersion;

	@Column
	private String name;

	@Column
	private Boolean isCollectionFacility;

	@Column
	private Boolean isPublic;

	@Column
	private Boolean displayMayInvolveCollectors;

	@Column
	private Boolean displayOrganic;

	@Column
	private Boolean displayPriceDeductionDamage;

	@Column
	private Boolean displayTare;

	@Column
	private Boolean displayWomenOnly;

	@OneToOne(cascade = CascadeType.ALL)
	private FacilityLocation facilityLocation;

	@ManyToOne
	private Company company;

	@OneToOne(cascade = CascadeType.ALL)
	private FacilityType facilityType;

	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FacilitySemiProduct> facilitySemiProducts = new ArrayList<>();
	
	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
	private List<StockOrder> stockOrders = new ArrayList<>();

	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FacilityTranslation> facilityTranslations = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsCollectionFacility() {
		return isCollectionFacility;
	}

	public void setIsCollectionFacility(Boolean isCollectionFacility) {
		this.isCollectionFacility = isCollectionFacility;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getDisplayMayInvolveCollectors() {
		return displayMayInvolveCollectors;
	}

	public void setDisplayMayInvolveCollectors(Boolean displayMayInvolveCollectors) {
		this.displayMayInvolveCollectors = displayMayInvolveCollectors;
	}

	public Boolean getDisplayOrganic() {
		return displayOrganic;
	}

	public void setDisplayOrganic(Boolean displayOrganic) {
		this.displayOrganic = displayOrganic;
	}

	public Boolean getDisplayPriceDeductionDamage() {
		return displayPriceDeductionDamage;
	}

	public void setDisplayPriceDeductionDamage(Boolean displayPriceDeductionDamage) {
		this.displayPriceDeductionDamage = displayPriceDeductionDamage;
	}

	public Boolean getDisplayTare() {
		return displayTare;
	}

	public void setDisplayTare(Boolean displayTare) {
		this.displayTare = displayTare;
	}

	public Boolean getDisplayWomenOnly() {
		return displayWomenOnly;
	}

	public void setDisplayWomenOnly(Boolean displayWomenOnly) {
		this.displayWomenOnly = displayWomenOnly;
	}

	public FacilityLocation getFacilityLocation() {
		return facilityLocation;
	}

	public void setFacilityLocation(FacilityLocation facilityLocation) {
		this.facilityLocation = facilityLocation;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public List<FacilitySemiProduct> getFacilitySemiProducts() {
		return facilitySemiProducts;
	}

	public void setFacilitySemiProducts(List<FacilitySemiProduct> facilitySemiProducts) {
		this.facilitySemiProducts = facilitySemiProducts;
	}
	
	public List<StockOrder> getStockOrders() {
		return stockOrders;
	}

	public void setStockOrders(List<StockOrder> stockOrders) {
		this.stockOrders = stockOrders;
	}

	public List<FacilityTranslation> getFacilityTranslations() {
		return facilityTranslations;
	}

	public void setFacilityTranslations(List<FacilityTranslation> facilityTranslations) {
		this.facilityTranslations = facilityTranslations;
	}

	public Facility() {
		super();
	}

	public Facility(String name, Boolean isCollectionFacility, Boolean isPublic,
			FacilityLocation facilityLocation, Company company, FacilityType facilityType,
			List<FacilitySemiProduct> facilitySemiProducts, List<StockOrder> stockOrders, List<FacilityTranslation> facilityTranslations) {
		super();
		this.name = name;
		this.isCollectionFacility = isCollectionFacility;
		this.isPublic = isPublic;
		this.facilityLocation = facilityLocation;
		this.company = company;
		this.facilityType = facilityType;
		this.facilitySemiProducts = facilitySemiProducts;
		this.stockOrders = stockOrders;
		this.facilityTranslations = facilityTranslations;
	}

}
