package com.abelium.inatrace.components.stockorder.api;

import java.math.BigDecimal;

public class ApiDeliveriesTotalItem {

	private String unit; // year (int) /month (0 - 11) /week (0-51) /day (01.01. -- 31.12..)
	private BigDecimal totalQuantity;

	public ApiDeliveriesTotalItem(String unit, BigDecimal totalQuantity) {
		this.unit = unit;
		this.totalQuantity = totalQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
