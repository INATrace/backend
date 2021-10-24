package com.abelium.inatrace.db.entities.productorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductOrder extends TimestampEntity {

	@ManyToOne(optional = false)
	private Facility facility;

	@Column(nullable = false)
	private Instant deliveryDeadline;

	@ManyToOne
	private CompanyCustomer customer;

	@Column
	private Boolean requiredWomensOnly;

	@Column
	private Boolean requiredOrganic;

	@ManyToOne
	private GradeAbbreviationType requiredGrade;

	@OneToMany(mappedBy = "productOrder")
	private List<StockOrder> items;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Instant getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(Instant deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	public CompanyCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(CompanyCustomer customer) {
		this.customer = customer;
	}

	public Boolean getRequiredWomensOnly() {
		return requiredWomensOnly;
	}

	public void setRequiredWomensOnly(Boolean requiredWomensOnly) {
		this.requiredWomensOnly = requiredWomensOnly;
	}

	public Boolean getRequiredOrganic() {
		return requiredOrganic;
	}

	public void setRequiredOrganic(Boolean requiredOrganic) {
		this.requiredOrganic = requiredOrganic;
	}

	public GradeAbbreviationType getRequiredGrade() {
		return requiredGrade;
	}

	public void setRequiredGrade(GradeAbbreviationType requiredGrade) {
		this.requiredGrade = requiredGrade;
	}

	public List<StockOrder> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<StockOrder> items) {
		this.items = items;
	}

}