package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "Payment.listPaymentsByCompany", 
				query = "SELECT p FROM Payment p "
						+ "INNER JOIN FETCH p.payingCompany pc "
						+ "INNER JOIN FETCH p.stockOrder so "
						+ "WHERE pc.id = :companyId"),
	@NamedQuery(name = "Payment.countPaymentsByCompany",
	            query = "SELECT COUNT(p) FROM Payment p "
						+ "INNER JOIN p.payingCompany pc "
						+ "WHERE pc.id = :companyId")
})
public class Payment extends TimestampEntity {

	@Version
	private Long entityVersion;

	@ManyToOne(optional = false)
	private User createdBy;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentType paymentType;

	@Column
	private String currency;

	@Column
	private BigDecimal amount;
	
	@Column
	private BigDecimal amountPaidToTheCollector;
	
	@OneToOne
	private StockOrder stockOrder;
	
	@OneToOne // TODO: check relationship and how it fits here ?
	private StockOrder order;
	
	@OneToMany // TODO: check relationship and how it fits here ?
	private List<Transaction> inputTransactions = new ArrayList<>();
	
	@ManyToOne
	private Company payingCompany;
	
	@ManyToOne
	private Company recipientCompany; // TODO: is this a company receiving a payment?
	
	@ManyToOne
	private UserCustomer recipientUserCustomer;
	
	@ManyToOne
	private Company representativeOfRecipientCompany;
	
	@ManyToOne
	private UserCustomer representativeOfRecipientUserCustomer;
	
	@ManyToOne
	private CompanyCustomer recipientCompanyCustomer;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private RecipientType recipientType;
	
	@Column
	private Long receiptNumber;

	@OneToOne
	private Document receiptDocument;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private ReceiptDocumentType receiptDocumentType;
	
	@ManyToOne
	private BulkPayment bankTransfer;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentPurposeType paymentPurporseType;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentStatus paymentStatus;
	
	@ManyToOne
	private User paymentConfirmedByUser;
	
	@ManyToOne
	private Company paymentConfirmedByCompany;
    
    @Column
    private Instant paymentConfirmedAtTime;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PreferredWayOfPayment preferredWayOfPayment;
	
	@Column
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

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public StockOrder getOrder() {
		return order;
	}

	public void setOrder(StockOrder order) {
		this.order = order;
	}

	public List<Transaction> getInputTransactions() {
		return inputTransactions;
	}

	public void setInputTransactions(List<Transaction> inputTransactions) {
		this.inputTransactions = inputTransactions;
	}

	public Company getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(Company payingCompany) {
		this.payingCompany = payingCompany;
	}

	public Company getRecipientCompany() {
		return recipientCompany;
	}

	public void setRecipientCompany(Company recipientCompany) {
		this.recipientCompany = recipientCompany;
	}

	public UserCustomer getRecipientUserCustomer() {
		return recipientUserCustomer;
	}

	public void setRecipientUserCustomer(UserCustomer recipientUserCustomer) {
		this.recipientUserCustomer = recipientUserCustomer;
	}

	public Company getRepresentativeOfRecipientCompany() {
		return representativeOfRecipientCompany;
	}

	public void setRepresentativeOfRecipientCompany(Company representativeOfRecipientCompany) {
		this.representativeOfRecipientCompany = representativeOfRecipientCompany;
	}

	public UserCustomer getRepresentativeOfRecipientUserCustomer() {
		return representativeOfRecipientUserCustomer;
	}

	public void setRepresentativeOfRecipientUserCustomer(UserCustomer representativeOfRecipientUserCustomer) {
		this.representativeOfRecipientUserCustomer = representativeOfRecipientUserCustomer;
	}

	public CompanyCustomer getRecipientCompanyCustomer() {
		return recipientCompanyCustomer;
	}

	public void setRecipientCompanyCustomer(CompanyCustomer recipientCompanyCustomer) {
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

	public Document getReceiptDocument() {
		return receiptDocument;
	}

	public void setReceiptDocument(Document receiptDocument) {
		this.receiptDocument = receiptDocument;
	}

	public ReceiptDocumentType getReceiptDocumentType() {
		return receiptDocumentType;
	}

	public void setReceiptDocumentType(ReceiptDocumentType receiptDocumentType) {
		this.receiptDocumentType = receiptDocumentType;
	}

	public BulkPayment getBankTransfer() {
		return bankTransfer;
	}

	public void setBankTransfer(BulkPayment bankTransfer) {
		this.bankTransfer = bankTransfer;
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

	public User getPaymentConfirmedByUser() {
		return paymentConfirmedByUser;
	}

	public void setPaymentConfirmedByUser(User paymentConfirmedByUser) {
		this.paymentConfirmedByUser = paymentConfirmedByUser;
	}

	public Company getPaymentConfirmedByCompany() {
		return paymentConfirmedByCompany;
	}

	public void setPaymentConfirmedByCompany(Company paymentConfirmedByCompany) {
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
