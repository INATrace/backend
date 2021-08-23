package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.db.entities.company.Company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = { @Index(columnList = "name") })
public class Facility extends TimestampEntity {

	@Column
	private Long entityVersion;

	@Column
	private String name;

	@Column
	private Boolean isCollectionFacility;

	@Column
	private Boolean isPublic;

	@OneToOne
	private FacilityLocation facilityLocation;

	@ManyToOne
	private Company company;

	@OneToOne
	private FacilityType facilityType;

	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
	private List<FacilitySemiProduct> facilitySemiProducts = new ArrayList<>();

	public Long getEntityVersion() {
		return entityVersion;
	}

	public void setEntityVersion(Long entityVersion) {
		this.entityVersion = entityVersion;
	}

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

	public Facility() {
		super();
	}

	public Facility(Long entityVersion, String name, Boolean isCollectionFacility, Boolean isPublic,
			FacilityLocation facilityLocation, Company company, FacilityType facilityType,
			List<FacilitySemiProduct> facilitySemiProducts) {
		super();
		this.entityVersion = entityVersion;
		this.name = name;
		this.isCollectionFacility = isCollectionFacility;
		this.isPublic = isPublic;
		this.facilityLocation = facilityLocation;
		this.company = company;
		this.facilityType = facilityType;
		this.facilitySemiProducts = facilitySemiProducts;
	}

}
