package com.abelium.inatrace.components.productorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiProductOrder extends ApiBaseEntity {

	@Schema(description = "The order ID entered by the user")
	private String orderId;

	@Schema(description = "Timestamp indicates when product order have been updated")
	private Instant updateTimestamp;

	@Schema(description = "Facility to which this order is order is ordered")
	private ApiFacility facility;

	@Schema(description = "The delivery deadline of this order")
	private LocalDate deliveryDeadline;

	@Schema(description = "The company customer for whom this order is placed")
	private ApiCompanyCustomer customer;

	@Schema(description = "Require only women's coffee")
	private Boolean requiredWomensOnly;

	@Schema(description = "Require organic coffee")
	private Boolean requiredOrganic;

	@Schema(description = "The ordered items(final products) of this order")
	private List<ApiStockOrder> items;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Instant getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Instant updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public ApiFacility getFacility() {
		return facility;
	}

	public void setFacility(ApiFacility facility) {
		this.facility = facility;
	}

	public LocalDate getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(LocalDate deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}

	public ApiCompanyCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ApiCompanyCustomer customer) {
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

	public List<ApiStockOrder> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<ApiStockOrder> items) {
		this.items = items;
	}

}
