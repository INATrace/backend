package com.abelium.inatrace.components.dashboard.api;

import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;

import java.util.List;

public class ApiProcessingPerformanceTotal {

	private ApiAggregationTimeUnit unitType;

	private ApiMeasureUnitType measureUnitType;
	private List<ApiProcessingPerformanceTotalItem> totals;

	public ApiProcessingPerformanceTotal(ApiAggregationTimeUnit unitType, ApiMeasureUnitType measureUnitType,
	                                     List<ApiProcessingPerformanceTotalItem> totals) {
		this.unitType = unitType;
		this.measureUnitType = measureUnitType;
		this.totals = totals;
	}

	public ApiAggregationTimeUnit getUnitType() {
		return unitType;
	}

	public void setUnitType(ApiAggregationTimeUnit unitType) {
		this.unitType = unitType;
	}

	public ApiMeasureUnitType getMeasureUnitType() {
		return measureUnitType;
	}

	public void setMeasureUnitType(ApiMeasureUnitType measureUnitType) {
		this.measureUnitType = measureUnitType;
	}

	public List<ApiProcessingPerformanceTotalItem> getTotals() {
		return totals;
	}

	public void setTotals(List<ApiProcessingPerformanceTotalItem> totals) {
		this.totals = totals;
	}
}
