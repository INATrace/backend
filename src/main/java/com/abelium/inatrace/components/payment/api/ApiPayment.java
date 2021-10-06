package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.payment.PaymentType;
import com.abelium.inatrace.db.entities.payment.ReceiptDocumentType;
import com.abelium.inatrace.db.entities.payment.RecipientType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.annotations.ApiModelProperty;

public class ApiPayment extends ApiBaseEntity {

	@ApiModelProperty(value = "Payment created by user")
	private User createdBy;

	@ApiModelProperty(value = "Payment type")
	private PaymentType paymentType;

	@ApiModelProperty(value = "Payment's currency")
	private String currency;

	@ApiModelProperty(value = "Payment amount")
	private BigDecimal amount;
	
	@ApiModelProperty(value = "Payment amount paid to the collector")
	private BigDecimal amountPaidToTheCollector;
	
	@ApiModelProperty(value = "Stock order related to the payment")
	private ApiStockOrder stockOrder;
	
//	@ApiModelProperty(value = "")
//	private StockOrder order;
	
//	@ApiModelProperty(value = "")
//	private List<Transaction> inputTransactions = new ArrayList<>();
	
	@ApiModelProperty(value = "Company that is making the payment")
	private ApiCompanyBase payingCompany;
	
	@ApiModelProperty(value = "Company that receives the payment")
	private ApiCompanyBase recipientCompany; // TODO: is this a company receiving a payment?
	
	@ApiModelProperty(value = "User customer who receives the payment")
	private ApiUserCustomer recipientUserCustomer;
	
	@ApiModelProperty(value = "Representative of the company that receives the payment")
	private ApiCompanyBase representativeOfRecipientCompany;
	
	@ApiModelProperty(value = "Representative of the user customer that receives the payment")
	private ApiUserCustomer representativeOfRecipientUserCustomer;
	
	@ApiModelProperty(value = "Company customer that receives the payment")
	private ApiCompanyCustomer recipientCompanyCustomer;
	
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
	private PaymentPurposeType paymentPurporseType;
	
	@ApiModelProperty(value = "Payment status")
	private PaymentStatus paymentStatus;
	
	@ApiModelProperty(value = "User who confirmed the payment")
	private ApiUser paymentConfirmedByUser;
	
	@ApiModelProperty(value = "Company that confirmed the payment")
	private ApiCompanyBase paymentConfirmedByCompany;
    
	@ApiModelProperty(value = "Payment time confirmation")
    private Instant paymentConfirmedAtTime;
	
	@ApiModelProperty(value = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;
	
	@ApiModelProperty(value = "Production date")
    private Instant productionDate;

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public ApiStockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(ApiStockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public ApiCompanyBase getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(ApiCompanyBase payingCompany) {
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

	public ApiCompanyBase getRepresentativeOfRecipientCompany() {
		return representativeOfRecipientCompany;
	}

	public void setRepresentativeOfRecipientCompany(ApiCompanyBase representativeOfRecipientCompany) {
		this.representativeOfRecipientCompany = representativeOfRecipientCompany;
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

	public PaymentPurposeType getPaymentPurporseType() {
		return paymentPurporseType;
	}

	public void setPaymentPurporseType(PaymentPurposeType paymentPurporseType) {
		this.paymentPurporseType = paymentPurporseType;
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

	public ApiCompanyBase getPaymentConfirmedByCompany() {
		return paymentConfirmedByCompany;
	}

	public void setPaymentConfirmedByCompany(ApiCompanyBase paymentConfirmedByCompany) {
		this.paymentConfirmedByCompany = paymentConfirmedByCompany;
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
	
}
