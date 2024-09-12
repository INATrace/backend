package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.Company;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BulkPayment extends TimestampEntity {

	@Version
	private Long entityVersion;
	
	@ManyToOne(optional = false)
	private User createdBy;
	
	@Column
	private String currency;
	
	@Column
    private Instant formalCreationTime;
	
	@ManyToOne
	private Company payingCompany;
	
	@Lob
	private String paymentDescription;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PaymentPurposeType paymentPurposeType;
	
	@Column
	private String receiptNumber;
	
	@Column
	private BigDecimal totalAmount;
	
	@Column
	private BigDecimal additionalCost;
	
	@Column
	private String additionalCostDescription;
	
	@OneToMany(mappedBy = "bulkPayment")
	private List<Payment> payments = new ArrayList<>();

	@OneToMany(mappedBy = "bulkPayment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BulkPaymentActivityProof> additionalProofs;

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

	public Company getPayingCompany() {
		return payingCompany;
	}

	public void setPayingCompany(Company payingCompany) {
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

	public List<Payment> getPayments() {
		if (payments == null)
			payments = new ArrayList<>();
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public List<BulkPaymentActivityProof> getAdditionalProofs() {
		if (additionalProofs == null)
			additionalProofs = new ArrayList<>();
		return additionalProofs;
	}

	public void setAdditionalProofs(List<BulkPaymentActivityProof> additionalProofs) {
		this.additionalProofs = additionalProofs;
	}
}
