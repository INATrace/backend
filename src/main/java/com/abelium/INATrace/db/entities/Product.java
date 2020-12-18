package com.abelium.INATrace.db.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.types.ProductStatus;

@Entity
@Table(indexes = { @Index(columnList = "name") })
public class Product extends ProductContent {
	
	@Version
	private long entityVersion;
	
	/**
	 * product status
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private ProductStatus status = ProductStatus.DISABLED;
	
	/**
	 * origin - farmer location - Use the pins on the map to mark locations of farmers or suppliers and 
	 * indicate the number of farmers or suppliers you work with directly or indirectly at each location. 
	 */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductLocation> originLocations = new ArrayList<>(0);		
	
	/**
	 * company - owner
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * a list of other companies associated with this product (buyers, producers, ...) 
	 */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductCompany> associatedCompanies = new ArrayList<>();	

	/**
	 * a list of "collectors" 
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<UserCustomer> collectors = new ArrayList<>();	
    
	/**
	 * labels
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<ProductLabel> labels = new ArrayList<>();
		

	public List<ProductLocation> getOriginLocations() {
		return originLocations;
	}

	public void setOriginLocations(List<ProductLocation> originLocations) {
		this.originLocations = originLocations;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public List<ProductCompany> getAssociatedCompanies() {
		return associatedCompanies;
	}

	public void setAssociatedCompanies(List<ProductCompany> associatedCompanies) {
		this.associatedCompanies = associatedCompanies;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public List<ProductLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<ProductLabel> labels) {
		this.labels = labels;
	}

	public List<UserCustomer> getCollectors() {
		return collectors;
	}

	public void setCollectors(List<UserCustomer> collectors) {
		this.collectors = collectors;
	}
}
