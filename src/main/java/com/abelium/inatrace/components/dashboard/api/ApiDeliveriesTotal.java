package com.abelium.inatrace.components.dashboard.api;

import java.util.List;

public class ApiDeliveriesTotal {

	private ApiAggregationTimeUnit unitType;

	private List<ApiDeliveriesTotalItem> totals;

	public ApiDeliveriesTotal(ApiAggregationTimeUnit unitType, List<ApiDeliveriesTotalItem> totals) {
		this.unitType = unitType;
		this.totals = totals;
	}

	public ApiAggregationTimeUnit getUnitType() {
		return unitType;
	}

	public void setUnitType(ApiAggregationTimeUnit unitType) {
		this.unitType = unitType;
	}

	public List<ApiDeliveriesTotalItem> getTotals() {
		return totals;
	}

	public void setTotals(List<ApiDeliveriesTotalItem> totals) {
		this.totals = totals;
	}
}
