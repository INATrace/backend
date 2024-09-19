package com.abelium.inatrace.db.entities.productorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductOrder extends TimestampEntity {

	@Column
	private String orderId;

	@ManyToOne(optional = false)
	private Facility facility;

	@Column(nullable = false)
	private LocalDate deliveryDeadline;

	@ManyToOne
	private CompanyCustomer customer;

	@Column
	private Boolean requiredWomensOnly;

	@Column
	private Boolean requiredOrganic;

	@OneToMany(mappedBy = "productOrder")
	private Set<StockOrder> items;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public LocalDate getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(LocalDate deliveryDeadline) {
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

	public Set<StockOrder> getItems() {
		if (items == null) {
			items = new HashSet<>();
		}
		return items;
	}

	public void setItems(Set<StockOrder> items) {
		this.items = items;
	}

}
