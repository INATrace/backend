package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public class ApiPurchaseOrderFarmer extends ApiBaseEntity {

	@Schema(description = "Purchase order identifier")
	private String identifier;

	@Schema(description = "Id of the person who has produced the entry.")
	private ApiUserCustomer producerUserCustomer;

	@Schema(description = "Semi product")
	private ApiSemiProduct semiProduct;

	@Schema(description = "Total quantity")
	private BigDecimal totalQuantity;

	@Schema(description = "Total gross quantity")
	private BigDecimal totalGrossQuantity;

	@Schema(description = "Fulfilled quantity")
	private BigDecimal fulfilledQuantity;

	@Schema(description = "Available quantity")
	private BigDecimal availableQuantity;

	@Schema(description = "Price per unit")
	private BigDecimal pricePerUnit;

	@Schema(description = "Tare")
	private BigDecimal tare;

	@Schema(description = "Damaged price deduction")
	private BigDecimal damagedPriceDeduction;

	@Schema(description = "Damaged weight deduction")
	private BigDecimal damagedWeightDeduction;

	@Schema(description = "Organic")
	private Boolean organic;

	@Schema(description = "Women only")
	private Boolean womenShare;

	@Schema(description = "Cost")
	public BigDecimal cost;

	@Schema(description = "Balance")
	public BigDecimal balance;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ApiUserCustomer getProducerUserCustomer() {
		return producerUserCustomer;
	}

	public void setProducerUserCustomer(ApiUserCustomer producerUserCustomer) {
		this.producerUserCustomer = producerUserCustomer;
	}

	public ApiSemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(ApiSemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTotalGrossQuantity() {
		return totalGrossQuantity;
	}

	public void setTotalGrossQuantity(BigDecimal totalGrossQuantity) {
		this.totalGrossQuantity = totalGrossQuantity;
	}

	public BigDecimal getFulfilledQuantity() {
		return fulfilledQuantity;
	}

	public void setFulfilledQuantity(BigDecimal fulfilledQuantity) {
		this.fulfilledQuantity = fulfilledQuantity;
	}

	public BigDecimal getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(BigDecimal availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getTare() {
		return tare;
	}

	public void setTare(BigDecimal tare) {
		this.tare = tare;
	}

	public BigDecimal getDamagedPriceDeduction() {
		return damagedPriceDeduction;
	}

	public void setDamagedPriceDeduction(BigDecimal damagedPriceDeduction) {
		this.damagedPriceDeduction = damagedPriceDeduction;
	}

	public BigDecimal getDamagedWeightDeduction() {
		return damagedWeightDeduction;
	}

	public void setDamagedWeightDeduction(BigDecimal damagedWeightDeduction) {
		this.damagedWeightDeduction = damagedWeightDeduction;
	}

	public Boolean getOrganic() {
		return organic;
	}

	public void setOrganic(Boolean organic) {
		this.organic = organic;
	}

	public Boolean getWomenShare() {
		return womenShare;
	}

	public void setWomenShare(Boolean womenShare) {
		this.womenShare = womenShare;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
