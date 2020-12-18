package com.abelium.INATrace.db.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

@Entity
@Table(indexes = { @Index(columnList = "number") }, uniqueConstraints = {
		@UniqueConstraint(columnNames = { "label_id", "number" }) })
public class ProductLabelBatch extends BaseEntity {
	
	@Version
	private long entityVersion;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductLabel label;
	
	/**
	 * batch "number"
	 */
	@Column(length = Lengths.UID)
	private String number;
	
	/**
	 * Production date
	 */
	@Column
	private LocalDate productionDate;

	/**
	 * Expiry date
	 */
	@Column
	private LocalDate expiryDate;
	
	/**
	 * locations 
	 */
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchLocation> locations = new ArrayList<>(0);
    
	/**
	 * photo 
	 */
	@ManyToOne
	private Document photo;

	/**
	 * Enable check authenticity
	 */
	@Column
	private Boolean checkAuthenticity;

	/**
	 * Enable trace origin
	 */
	@Column
	private Boolean traceOrigin;
	
	
	public ProductLabel getLabel() {
		return label;
	}

	public void setLabel(ProductLabel label) {
		this.label = label;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public List<BatchLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<BatchLocation> locations) {
		this.locations = locations;
	}

	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	public Boolean getCheckAuthenticity() {
		return checkAuthenticity;
	}

	public void setCheckAuthenticity(Boolean checkAuthenticity) {
		this.checkAuthenticity = checkAuthenticity;
	}

	public Boolean getTraceOrigin() {
		return traceOrigin;
	}

	public void setTraceOrigin(Boolean traceOrigin) {
		this.traceOrigin = traceOrigin;
	}
	
}
