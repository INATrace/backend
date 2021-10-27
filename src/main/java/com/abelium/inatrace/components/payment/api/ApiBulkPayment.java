package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ApiBulkPayment extends ApiBaseEntity {
	
	@ApiModelProperty(value = "Bulk payment created by")
	private ApiUser createdBy;

	@ApiModelProperty(value = "Creation timestamp")
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant creationTimestamp;

	@ApiModelProperty(value = "Bulk payment currency")
	private String currency;

	@ApiModelProperty(value = "Formal creation date")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant formalCreationTime;
	
	@ApiModelProperty(value = "Company that pays for the bulk payment")
	private ApiCompany payingCompany;
	
	@ApiModelProperty(value = "Bulk payment drescription")
	private String paymentDescription;
	
	@ApiModelProperty(value = "Bulk payment purpose type")
	private PaymentPurposeType paymentPurposeType;
	
	@ApiModelProperty(value = "Bulk payment receipt number")
	private String receiptNumber;
	
	@ApiModelProperty(value = "Bulk payment total amount")
	private BigDecimal totalAmount;
	
	@ApiModelProperty(value = "Bulk payment additional cost")
	private BigDecimal additionalCost;
	
	@ApiModelProperty(value = "Bulk payment additional cost description")
	private String additionalCostDescription;
	
	@ApiModelProperty(value = "Bulk payment payments")
	private List<ApiPayment> payments = new ArrayList<>();
	
	@ApiModelProperty(value = "Bulk payment additional proofs")
	private List<ApiActivityProof> additionalProofs = new ArrayList<>();

	public ApiUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ApiUser createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Instant getFormalCreationTime() {
		return formalCreationTime;
	}

	public void setFormalCreationTime(Instant formalCreationTime) {
		this.formalCreationTime = formalCreationTime;
	}

	public ApiCompany getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(ApiCompany payingCompany) {
		this.payingCompany = payingCompany;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public PaymentPurposeType getPaymentPurposeType() {
		return paymentPurposeType;
	}

	public void setPaymentPurposeType(PaymentPurposeType paymentPurposeType) {
		this.paymentPurposeType = paymentPurposeType;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(BigDecimal additionalCost) {
		this.additionalCost = additionalCost;
	}

	public String getAdditionalCostDescription() {
		return additionalCostDescription;
	}

	public void setAdditionalCostDescription(String additionalCostDescription) {
		this.additionalCostDescription = additionalCostDescription;
	}

	public List<ApiPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<ApiPayment> payments) {
		this.payments = payments;
	}

	public List<ApiActivityProof> getAdditionalProofs() {
		return additionalProofs;
	}

	public void setAdditionalProofs(List<ApiActivityProof> additionalProofs) {
		this.additionalProofs = additionalProofs;
	}
}
