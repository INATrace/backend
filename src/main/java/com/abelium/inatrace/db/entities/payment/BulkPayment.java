package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.BankInformation;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class BulkPayment extends BaseEntity {

	@Version
	private Long entityVersion;
	
	@ManyToOne(optional = false)
	private User createdBy;
	
	@Column
	private String currency;
	
	@Column
    private Instant formalCreationTime;
	
	@Column
	private BankInformation bankInfo;
	
	@Column
	private Long receiptNumber;

	@ManyToOne
	private Company payingCompany;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentPurposeType paymentPurporseType;

	@Column
	private String paymentDescription;

	@Column
	private BigDecimal totalAmount;
	
	@Column
	private BigDecimal paymentPerKg;
	
	@Column
	private BigDecimal additionalCost;
	
	@Column
	private String additionalCostDescription;
	
	@OneToMany
	private List<StockOrder> stockOrders = new ArrayList<>();
	
	@OneToMany
	private List<ActivityProof> additionalProofs = new ArrayList<>();
	
	@OneToMany
	private List<Payment> payments = new ArrayList<>();

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public Long getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Long receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Company getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(Company payingCompany) {
		this.payingCompany = payingCompany;
	}

	public PaymentPurposeType getPaymentPurporseType() {
		return paymentPurporseType;
	}

	public void setPaymentPurporseType(PaymentPurposeType paymentPurporseType) {
		this.paymentPurporseType = paymentPurporseType;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPaymentPerKg() {
		return paymentPerKg;
	}

	public void setPaymentPerKg(BigDecimal paymentPerKg) {
		this.paymentPerKg = paymentPerKg;
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

	public List<StockOrder> getStockOrders() {
		return stockOrders;
	}

	public void setStockOrders(List<StockOrder> stockOrders) {
		this.stockOrders = stockOrders;
	}

	public List<ActivityProof> getAdditionalProofs() {
		return additionalProofs;
	}

	public void setAdditionalProofs(List<ActivityProof> additionalProofs) {
		this.additionalProofs = additionalProofs;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
}
