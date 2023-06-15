package com.abelium.inatrace.components.dashboard.api;

import java.math.BigDecimal;

public class ApiProcessingPerformanceTotalItem {

	private String unit; // year (int) /month (0 - 11) /week (0-51) /day (01.01. -- 31.12..)
	private Integer year;
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

	public ApiProcessingPerformanceTotalItem(String unit, Integer year, BigDecimal inputQuantity,
	                                         BigDecimal outputQuantity, BigDecimal ratio) {
		this.unit = unit;
		this.year = year;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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
