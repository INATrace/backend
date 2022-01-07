package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;

@Validated
public class ApiPurchaseOrder extends ApiBaseEntity {

	@ApiModelProperty(value = "Timestamp indicates when purchase order have been updated", position = 2)
	private Instant updateTimestamp;

	@ApiModelProperty(value = "User that has created PurchaseOrder")
	private ApiUser createdBy;

	@ApiModelProperty(value = "ID of the user who has created the stock order")
	public Long creatorId;

	@ApiModelProperty(value = "User that has last updated PurchaseOrder")
	private ApiUser updatedBy;

	@ApiModelProperty(value = "Delivery date")
	private Instant deliveryTime;

	@ApiModelProperty(value = "Production date")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	public Instant productionDate;

	@ApiModelProperty(value = "Currency")
	public String currency;

	@ApiModelProperty(value = "Representative of producer user customer. E.g. collector.")
	public ApiUserCustomer representativeOfProducerUserCustomer;

	private List<ApiPurchaseOrderFarmer> farmers;

	@ApiModelProperty(value = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;

	@ApiModelProperty(value = "Facility")
	public ApiFacility facility;

	@ApiModelProperty(value = "Activity proofs")
	public List<ApiActivityProof> activityProofs;

	public Instant getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Instant updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public ApiUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ApiUser createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public ApiUser getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(ApiUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Instant deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Instant getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Instant productionDate) {
		this.productionDate = productionDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ApiUserCustomer getRepresentativeOfProducerUserCustomer() {
		return representativeOfProducerUserCustomer;
	}

	public void setRepresentativeOfProducerUserCustomer(ApiUserCustomer representativeOfProducerUserCustomer) {
		this.representativeOfProducerUserCustomer = representativeOfProducerUserCustomer;
	}

	public List<ApiPurchaseOrderFarmer> getFarmers() {
		return farmers;
	}

	public void setFarmers(List<ApiPurchaseOrderFarmer> farmers) {
		this.farmers = farmers;
	}

	public PreferredWayOfPayment getPreferredWayOfPayment() {
		return preferredWayOfPayment;
	}

	public void setPreferredWayOfPayment(PreferredWayOfPayment preferredWayOfPayment) {
		this.preferredWayOfPayment = preferredWayOfPayment;
	}

	public ApiFacility getFacility() {
		return facility;
	}

	public void setFacility(ApiFacility facility) {
		this.facility = facility;
	}

	public List<ApiActivityProof> getActivityProofs() {
		return activityProofs;
	}

	public void setActivityProofs(List<ApiActivityProof> activityProofs) {
		this.activityProofs = activityProofs;
	}
}
