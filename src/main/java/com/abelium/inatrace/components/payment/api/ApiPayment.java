package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.payment.*;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class ApiPayment extends ApiBaseEntity {

	// From BaseEntity
	@ApiModelProperty(value = "Last updated timestamp")
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant updatedTimestamp;

	@ApiModelProperty(value = "Payment created by user")
	private ApiUser createdBy;
	
	@ApiModelProperty(value = "Payment updated by user")
	private ApiUser updatedBy;

	@ApiModelProperty(value = "Payment type")
	private PaymentType paymentType;

	@ApiModelProperty(value = "Payment's currency")
	private String currency;
	
	@ApiModelProperty(value = "Quantity purchased to be paid")
	private Integer purchased;

	@ApiModelProperty(value = "Payment amount paid (to a farmer or recipient company)")
	private BigDecimal amount;
	
	@ApiModelProperty(value = "Payment amount paid to the collector")
	private BigDecimal amountPaidToTheCollector;
	
	@ApiModelProperty(value = "Payment total amount")
	private BigDecimal totalPaid;
	
	@ApiModelProperty(value = "Stock order related to the payment")
	private ApiStockOrder stockOrder;
	
	@ApiModelProperty(value = "Recipient type")
	private RecipientType recipientType;
	
	@ApiModelProperty(value = "Receipt number")
	private String receiptNumber;

	@ApiModelProperty(value = "Receipt document")
	private ApiDocument receiptDocument;
	
	@ApiModelProperty(value = "Receipt document type")
	private ReceiptDocumentType receiptDocumentType;
	
	@ApiModelProperty(value = "Payment purpose type")
	private PaymentPurposeType paymentPurposeType;
	
	@ApiModelProperty(value = "Payment status")
	private PaymentStatus paymentStatus;
	
	@ApiModelProperty(value = "User who confirmed the payment")
	private ApiUser paymentConfirmedByUser;
	
	@ApiModelProperty(value = "Payment time confirmation")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    private Instant paymentConfirmedAtTime;

	@ApiModelProperty(value = "Formal creation date (for example: date on receipt)")
	private LocalDate formalCreationTime;
	
	@ApiModelProperty(value = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;
	
	@ApiModelProperty(value = "Production date")
    private LocalDate productionDate;

	@ApiModelProperty(value = "Paying company")
	private ApiCompany payingCompany;
	
	// The properties can be extracted from the purchase (stock order) on the way back
	
	@ApiModelProperty(value = "Company that receives the payment")
	private ApiCompanyBase recipientCompany;

	@ApiModelProperty(value = "User customer that receives the payment (farmer)")
	private ApiUserCustomer recipientUserCustomer;

	@ApiModelProperty(value = "Representative of the user customer that receives the payment (collector)")
	private ApiUserCustomer representativeOfRecipientUserCustomer;

	public Instant getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Instant updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public ApiUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ApiUser createdBy) {
		this.createdBy = createdBy;
	}
	
	public ApiUser getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(ApiUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getPurchased() {
		return purchased;
	}

	public void setPurchased(Integer purchased) {
		this.purchased = purchased;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountPaidToTheCollector() {
		return amountPaidToTheCollector;
	}

	public void setAmountPaidToTheCollector(BigDecimal amountPaidToTheCollector) {
		this.amountPaidToTheCollector = amountPaidToTheCollector;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public ApiStockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(ApiStockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public RecipientType getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public ApiDocument getReceiptDocument() {
		return receiptDocument;
	}

	public void setReceiptDocument(ApiDocument receiptDocument) {
		this.receiptDocument = receiptDocument;
	}

	public ReceiptDocumentType getReceiptDocumentType() {
		return receiptDocumentType;
	}

	public void setReceiptDocumentType(ReceiptDocumentType receiptDocumentType) {
		this.receiptDocumentType = receiptDocumentType;
	}

	public PaymentPurposeType getPaymentPurposeType() {
		return paymentPurposeType;
	}

	public void setPaymentPurposeType(PaymentPurposeType paymentPurposeType) {
		this.paymentPurposeType = paymentPurposeType;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ApiUser getPaymentConfirmedByUser() {
		return paymentConfirmedByUser;
	}

	public void setPaymentConfirmedByUser(ApiUser paymentConfirmedByUser) {
		this.paymentConfirmedByUser = paymentConfirmedByUser;
	}

	public Instant getPaymentConfirmedAtTime() {
		return paymentConfirmedAtTime;
	}

	public void setPaymentConfirmedAtTime(Instant paymentConfirmedAtTime) {
		this.paymentConfirmedAtTime = paymentConfirmedAtTime;
	}

	public LocalDate getFormalCreationTime() {
		return formalCreationTime;
	}

	public void setFormalCreationTime(LocalDate formalCreationTime) {
		this.formalCreationTime = formalCreationTime;
	}

	public PreferredWayOfPayment getPreferredWayOfPayment() {
		return preferredWayOfPayment;
	}

	public void setPreferredWayOfPayment(PreferredWayOfPayment preferredWayOfPayment) {
		this.preferredWayOfPayment = preferredWayOfPayment;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public ApiCompany getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(ApiCompany payingCompany) {
		this.payingCompany = payingCompany;
	}

	public ApiCompanyBase getRecipientCompany() {
		return recipientCompany;
	}

	public void setRecipientCompany(ApiCompanyBase recipientCompany) {
		this.recipientCompany = recipientCompany;
	}

	public ApiUserCustomer getRecipientUserCustomer() {
		return recipientUserCustomer;
	}

	public void setRecipientUserCustomer(ApiUserCustomer recipientUserCustomer) {
		this.recipientUserCustomer = recipientUserCustomer;
	}

	public ApiUserCustomer getRepresentativeOfRecipientUserCustomer() {
		return representativeOfRecipientUserCustomer;
	}

	public void setRepresentativeOfRecipientUserCustomer(ApiUserCustomer representativeOfRecipientUserCustomer) {
		this.representativeOfRecipientUserCustomer = representativeOfRecipientUserCustomer;
	}

}
