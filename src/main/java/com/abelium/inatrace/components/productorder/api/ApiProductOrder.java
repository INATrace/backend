package com.abelium.inatrace.components.productorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiProductOrder extends ApiBaseEntity {

	@ApiModelProperty(value = "The order ID entered by the user")
	private String orderId;

	@ApiModelProperty(value = "Timestamp indicates when product order have been updated")
	private Instant updateTimestamp;

	@ApiModelProperty(value = "Facility to which this order is order is ordered")
	private ApiFacility facility;

	@ApiModelProperty(value = "The delivery deadline of this order")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant deliveryDeadline;

	@ApiModelProperty(value = "The company customer for whom this order is placed")
	private ApiCompanyCustomer customer;

	@ApiModelProperty(value = "Require only women's coffee")
	private Boolean requiredWomensOnly;

	@ApiModelProperty(value = "Require organic coffee")
	private Boolean requiredOrganic;

	@ApiModelProperty(value = "The ordered items(final products) of this order")
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

	public Instant getDeliveryDeadline() {
		return deliveryDeadline;
	}

	public void setDeliveryDeadline(Instant deliveryDeadline) {
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
