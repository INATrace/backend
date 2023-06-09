package com.abelium.inatrace.components.stockorder.api;

import java.util.List;

public class ApiProcessingPerformanceTotal {

	private ApiAggregationTimeUnit unitType;
	private List<ApiProcessingPerformanceTotalItem> totals;

	public ApiProcessingPerformanceTotal(ApiAggregationTimeUnit unitType,
	                                     List<ApiProcessingPerformanceTotalItem> totals) {
		this.unitType = unitType;
		this.totals = totals;
	}

	public ApiAggregationTimeUnit getUnitType() {
		return unitType;
	}

	public void setUnitType(ApiAggregationTimeUnit unitType) {
		this.unitType = unitType;
	}

	public List<ApiProcessingPerformanceTotalItem> getTotals() {
		return totals;
	}

	public void setTotals(List<ApiProcessingPerformanceTotalItem> totals) {
		this.totals = totals;
	}
}
