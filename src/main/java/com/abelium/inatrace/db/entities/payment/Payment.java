package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "Payment.listPaymentsByPurchaseId", 
				query = "SELECT p FROM Payment p "
						+ "INNER JOIN p.stockOrder so "
						+ "WHERE so.id = :purchaseId"),
	@NamedQuery(name = "Payment.countPaymentsByPurchaseId",
	            query = "SELECT COUNT(p) FROM Payment p "
	            		+ "INNER JOIN p.stockOrder so "
						+ "WHERE so.id = :purchaseId"),
	@NamedQuery(name = "Payment.listPaymentsByCompanyId", 
				query = "SELECT p FROM Payment p "
						+ "INNER JOIN p.stockOrder so "
						+ "INNER JOIN so.company c "
						+ "WHERE c.id = :companyId"),
	@NamedQuery(name = "Payment.countPaymentsByCompanyId",
    			query = "SELECT COUNT(p) FROM Payment p "
    					+ "INNER JOIN p.stockOrder so "
						+ "INNER JOIN so.company c "
						+ "WHERE c.id = :companyId")
})
public class Payment extends TimestampEntity {

	@Version
	private Long entityVersion;
	
	@Column
	private String orderReference; // stock order identifier

	@ManyToOne(optional = false)
	private User createdBy; // logged-in user
	
	@ManyToOne
	private User updatedBy;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentType paymentType; // cash, bank

	@Column
	private String currency; // not chooseable on the frontend
	
	@Column
	private BigDecimal purchased; // stock order quantity

	@Column
	private BigDecimal amountPaidToTheFarmer; // same as stock order balance
	
	@Column
	private BigDecimal amountPaidToTheCollector; // set to 0 by default
	
	@Column
	private BigDecimal totalPaid;
	
	@ManyToOne
	private StockOrder stockOrder; // stock order to which the payment(s) belong to
	
//	@OneToOne // TODO: check relationship and how it fits here ?
//	private StockOrder order;
//	
//	@OneToMany // TODO: check relationship and how it fits here ?
//	private List<Transaction> inputTransactions = new ArrayList<>();
	
	@ManyToOne
	private Company payingCompany; // company who is paying - logged-in user
	
	@ManyToOne
	private Company recipientCompany; // farmer's company who is receiving payment
	
	@ManyToOne
	private Company representativeOfRecipientCompany; // collector's company who is receiving payment
	
	@ManyToOne
	private UserCustomer recipientUserCustomer; // farmer
	
	@ManyToOne
	private UserCustomer representativeOfRecipientUserCustomer; // collector
	
	@ManyToOne
	private CompanyCustomer recipientCompanyCustomer; // farmer geolocation, etc. ?
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private RecipientType recipientType; // organization, company customer or user customer (should be default)
	
	@Column
	private Long receiptNumber; // defined by user

	@OneToOne(cascade = CascadeType.ALL)
	private Document receiptDocument; // document info
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private ReceiptDocumentType receiptDocumentType; // purchase sheet, receipt
	
//	@ManyToOne
//	private BulkPayment bankTransfer;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentPurposeType paymentPurposeType; // advanced, cherry, member bonus, af women premium, invoice payment
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentStatus paymentStatus; // unconfirmed, confirmed
	
	@ManyToOne
	private User paymentConfirmedByUser; // user logged-in
	
	@ManyToOne
	private Company paymentConfirmedByCompany; // user's company who's logged-in
    
    @Column
    private Instant paymentConfirmedAtTime;

    @Column
	private Instant formalCreationTime;

	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PreferredWayOfPayment preferredWayOfPayment; // cash cooperative, cash collector, bank transfer
	
	@Column
    private Instant productionDate; // ?
	
	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
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

	public BigDecimal getAmountPaidToTheFarmer() {
		return amountPaidToTheFarmer;
	}

	public void setAmountPaidToTheFarmer(BigDecimal amount) {
		this.amountPaidToTheFarmer = amount;
	}

	public BigDecimal getAmountPaidToTheCollector() {
		return amountPaidToTheCollector;
	}

	public void setAmountPaidToTheCollector(BigDecimal amountPaidToTheCollector) {
		this.amountPaidToTheCollector = amountPaidToTheCollector;
	}
	
	public BigDecimal getPurchased() {
		return purchased;
	}

	public void setPurchased(BigDecimal purchased) {
		this.purchased = purchased;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

//	public StockOrder getOrder() {
//		return order;
//	}
//
//	public void setOrder(StockOrder order) {
//		this.order = order;
//	}
//
//	public List<Transaction> getInputTransactions() {
//		return inputTransactions;
//	}
//
//	public void setInputTransactions(List<Transaction> inputTransactions) {
//		this.inputTransactions = inputTransactions;
//	}

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

//	public BulkPayment getBankTransfer() {
//		return bankTransfer;
//	}
//
//	public void setBankTransfer(BulkPayment bankTransfer) {
//		this.bankTransfer = bankTransfer;
//	}

	public PaymentPurposeType getPaymentPurposeType() {
		return paymentPurposeType;
	}

	public void setPaymentPurposeType(PaymentPurposeType paymentPurporseType) {
		this.paymentPurposeType = paymentPurporseType;
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

	public Instant getFormalCreationTime() {
		return formalCreationTime;
	}

	public void setFormalCreationTime(Instant formalCreationTime) {
		this.formalCreationTime = formalCreationTime;
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
