package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.payment.PaymentType;
import com.abelium.inatrace.db.entities.payment.ReceiptDocumentType;
import com.abelium.inatrace.db.entities.payment.RecipientType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.Instant;

import io.swagger.annotations.ApiModelProperty;

public class ApiPayment extends ApiBaseEntity {

	@ApiModelProperty(value = "Payment created by user")
	private Long createdBy;
	
	@ApiModelProperty(value = "Payment updated by user")
	private Long updatedBy;

	@ApiModelProperty(value = "Payment type")
	private PaymentType paymentType;

	@ApiModelProperty(value = "Payment's currency")
	private String currency;
	
	@ApiModelProperty(value = "Quantity purchased to be paid")
	private Integer purchased;

	@ApiModelProperty(value = "Payment amount paid to the farmer")
	private Integer amountPaidToTheFarmer;
	
	@ApiModelProperty(value = "Payment amount paid to the collector")
	private Integer amountPaidToTheCollector;
	
	@ApiModelProperty(value = "Payment total amount")
	private Integer totalPaid;
	
	@ApiModelProperty(value = "Stock order related to the payment")
	private ApiStockOrder stockOrder;
	
//	@ApiModelProperty(value = "")
//	private StockOrder order;
	
//	@ApiModelProperty(value = "")
//	private List<Transaction> inputTransactions = new ArrayList<>();
	
	@ApiModelProperty(value = "Recipient type")
	private RecipientType recipientType;
	
	@ApiModelProperty(value = "Receipt number")
	private Long receiptNumber;

	@ApiModelProperty(value = "Receipt document")
	private ApiDocument receiptDocument;
	
	@ApiModelProperty(value = "Receipt document type")
	private ReceiptDocumentType receiptDocumentType;
	
//	@ApiModelProperty(value = "")
//	private BulkPayment bankTransfer;
	
	@ApiModelProperty(value = "Payment purpose type")
	private PaymentPurposeType paymentPurposeType;
	
	@ApiModelProperty(value = "Payment status")
	private PaymentStatus paymentStatus;
	
	@ApiModelProperty(value = "User who confirmed the payment")
	private ApiUser paymentConfirmedByUser;
	
	@ApiModelProperty(value = "Payment time confirmation")
    private Instant paymentConfirmedAtTime;
	
	@ApiModelProperty(value = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;
	
	@ApiModelProperty(value = "Production date")
    private Instant productionDate;
	
	// The next properties can be extracted from the purchase (stock order) on the way back
	
	@ApiModelProperty(value = "Company that receives the payment")
	private ApiCompanyBase recipientCompany; // TODO: is this a company receiving a payment?
	
	@ApiModelProperty(value = "Representative of the company that receives the payment")
	private ApiCompanyBase representativeOfRecipientCompany;

	@ApiModelProperty(value = "User customer that receives the payment (farmer)")
	private ApiUserCustomer recipientUserCustomer;

	@ApiModelProperty(value = "Representative of the user customer that receives the payment (collector)")
	private ApiUserCustomer representativeOfRecipientUserCustomer;
	
	@ApiModelProperty(value = "Company customer that receives the payment")
	private ApiCompanyCustomer recipientCompanyCustomer;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
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

	public Integer getAmountPaidToTheFarmer() {
		return amountPaidToTheFarmer;
	}

	public void setAmountPaidToTheFarmer(Integer amountPaidToTheFarmer) {
		this.amountPaidToTheFarmer = amountPaidToTheFarmer;
	}

	public Integer getAmountPaidToTheCollector() {
		return amountPaidToTheCollector;
	}

	public void setAmountPaidToTheCollector(Integer amountPaidToTheCollector) {
		this.amountPaidToTheCollector = amountPaidToTheCollector;
	}

	public Integer getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Integer totalPaid) {
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

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
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

	public void setPaymentPurporseType(PaymentPurposeType paymentPurposeType) {
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

	public PreferredWayOfPayment getPreferredWayOfPayment() {
		return preferredWayOfPayment;
	}

	public void setPreferredWayOfPayment(PreferredWayOfPayment preferredWayOfPayment) {
		this.preferredWayOfPayment = preferredWayOfPayment;
	}

	public Instant getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Instant productionDate) {
		this.productionDate = productionDate;
	}

	public ApiCompanyBase getRecipientCompany() {
		return recipientCompany;
	}

	public void setRecipientCompany(ApiCompanyBase recipientCompany) {
		this.recipientCompany = recipientCompany;
	}

	public ApiCompanyBase getRepresentativeOfRecipientCompany() {
		return representativeOfRecipientCompany;
	}

	public void setRepresentativeOfRecipientCompany(ApiCompanyBase representativeOfRecipientCompany) {
		this.representativeOfRecipientCompany = representativeOfRecipientCompany;
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

	public ApiCompanyCustomer getRecipientCompanyCustomer() {
		return recipientCompanyCustomer;
	}

	public void setRecipientCompanyCustomer(ApiCompanyCustomer recipientCompanyCustomer) {
		this.recipientCompanyCustomer = recipientCompanyCustomer;
	}

}
