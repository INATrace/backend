package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Validated
public class ApiPurchaseOrder extends ApiBaseEntity {

	@Schema(description = "Timestamp indicates when purchase order have been updated")
	private Instant updateTimestamp;

	@Schema(description = "User that has created PurchaseOrder")
	private ApiUser createdBy;

	@Schema(description = "ID of the user who has created the stock order")
	public Long creatorId;

	@Schema(description = "User that has last updated PurchaseOrder")
	private ApiUser updatedBy;

	@Schema(description = "Delivery date")
	private LocalDate deliveryTime;

	@Schema(description = "Production date")
	public LocalDate productionDate;

	@Schema(description = "Currency")
	public String currency;

	@Schema(description = "Representative of producer user customer. E.g. collector.")
	public ApiUserCustomer representativeOfProducerUserCustomer;

	private List<ApiPurchaseOrderFarmer> farmers;

	@Schema(description = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;

	@Schema(description = "Facility")
	public ApiFacility facility;

	@Schema(description = "Activity proofs")
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

	public LocalDate getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDate deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
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
