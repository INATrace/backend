package com.abelium.inatrace.components.stockorder.api;

import java.util.List;

public class ApiDeliveriesTotal {

	private ApiDeliveriesUnitType unitType;

	private List<ApiDeliveriesTotalItem> totals;

	public ApiDeliveriesTotal(ApiDeliveriesUnitType unitType, List<ApiDeliveriesTotalItem> totals) {
		this.unitType = unitType;
		this.totals = totals;
	}

	public ApiDeliveriesUnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(ApiDeliveriesUnitType unitType) {
		this.unitType = unitType;
	}

	public List<ApiDeliveriesTotalItem> getTotals() {
		return totals;
	}

	public void setTotals(List<ApiDeliveriesTotalItem> totals) {
		this.totals = totals;
	}
}
