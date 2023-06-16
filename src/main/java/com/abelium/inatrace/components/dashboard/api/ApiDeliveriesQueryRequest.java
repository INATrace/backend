package com.abelium.inatrace.components.dashboard.api;

import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;

import java.time.LocalDate;
import java.util.List;

public class ApiDeliveriesQueryRequest {

	public Long companyId;
	public List<Long> facilityIds;
	public Long farmerId;
	public Long representativeOfProducerUserCustomerId;

	public Long semiProductId;

	public OrderType orderType;
	public Boolean isWomenShare;
	public Boolean organicOnly;

	public LocalDate productionDateStart;

	public LocalDate productionDateEnd;

	public Boolean priceDeterminedLater;

	public ApiDeliveriesQueryRequest() {}

	// Used for delivery total calculation
	public ApiDeliveriesQueryRequest(Long companyId, List<Long> facilityIds, Long farmerId, Long representativeOfProducerUserCustomerId, Long semiProductId,
	                              Boolean isWomenShare, Boolean organicOnly, Boolean priceDeterminedLater, LocalDate productionDateStart,
	                              LocalDate productionDateEnd) {
		this.companyId = companyId;
		this.facilityIds = facilityIds;
		this.farmerId = farmerId;
		this.representativeOfProducerUserCustomerId = representativeOfProducerUserCustomerId;
		this.semiProductId = semiProductId;
		this.isWomenShare = isWomenShare;
		this.organicOnly = organicOnly;
		this.priceDeterminedLater = priceDeterminedLater;
		this.productionDateStart = productionDateStart;
		this.productionDateEnd = productionDateEnd;

	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<Long> getFacilityIds() {
		return facilityIds;
	}

	public void setFacilityIds(List<Long> facilityIds) {
		this.facilityIds = facilityIds;
	}

	public Long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Long farmerId) {
		this.farmerId = farmerId;
	}

	public Long getRepresentativeOfProducerUserCustomerId() {
		return representativeOfProducerUserCustomerId;
	}

	public void setRepresentativeOfProducerUserCustomerId(Long representativeOfProducerUserCustomerId) {
		this.representativeOfProducerUserCustomerId = representativeOfProducerUserCustomerId;
	}

	public Long getSemiProductId() {
		return semiProductId;
	}

	public void setSemiProductId(Long semiProductId) {
		this.semiProductId = semiProductId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Boolean getWomenShare() {
		return isWomenShare;
	}

	public void setWomenShare(Boolean womenShare) {
		isWomenShare = womenShare;
	}

	public Boolean getOrganicOnly() {
		return organicOnly;
	}

	public void setOrganicOnly(Boolean organicOnly) {
		this.organicOnly = organicOnly;
	}

	public LocalDate getProductionDateStart() {
		return productionDateStart;
	}

	public void setProductionDateStart(LocalDate productionDateStart) {
		this.productionDateStart = productionDateStart;
	}

	public LocalDate getProductionDateEnd() {
		return productionDateEnd;
	}

	public void setProductionDateEnd(LocalDate productionDateEnd) {
		this.productionDateEnd = productionDateEnd;
	}

	public Boolean getPriceDeterminedLater() {
		return priceDeterminedLater;
	}

	public void setPriceDeterminedLater(Boolean priceDeterminedLater) {
		this.priceDeterminedLater = priceDeterminedLater;
	}
}
