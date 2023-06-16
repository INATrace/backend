package com.abelium.inatrace.components.dashboard.api;

import java.math.BigDecimal;

public class ApiDeliveriesTotalItem {

	private String unit; // year (int) /month (0 - 11) /week (0-51) /day (01.01. -- 31.12..)
	private Integer year;
	private BigDecimal totalQuantity;

	public ApiDeliveriesTotalItem(String unit, Integer year, BigDecimal totalQuantity) {
		this.unit = unit;
		this.year = year;
		this.totalQuantity = totalQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
