package com.abelium.inatrace.components.stockorder.api;

import java.math.BigDecimal;

public class ApiProcessingPerformanceTotalItem {

	private String unit; // year (int) /month (0 - 11) /week (0-51) /day (01.01. -- 31.12..)
	private BigDecimal inputQuantity;
	private BigDecimal outputQuantity;
	private BigDecimal ratio; // output / input

	public ApiProcessingPerformanceTotalItem(String unit, BigDecimal inputQuantity, BigDecimal outputQuantity,
	                                         BigDecimal ratio) {
		this.unit = unit;
		this.inputQuantity = inputQuantity;
		this.outputQuantity = outputQuantity;
		this.ratio = ratio;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(BigDecimal inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public BigDecimal getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(BigDecimal outputQuantity) {
		this.outputQuantity = outputQuantity;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
}
