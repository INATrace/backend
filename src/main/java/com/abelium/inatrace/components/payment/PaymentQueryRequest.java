package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.LocalDate;

public class PaymentQueryRequest {

    public PaymentQueryRequest() { }

    public PaymentQueryRequest(Long companyId) {
        this.companyId = companyId;
    }

    public PaymentQueryRequest(Long companyId,
                               Long purchaseId,
                               PreferredWayOfPayment preferredWayOfPayment,
                               PaymentStatus paymentStatus,
                               LocalDate productionDateStart,
                               LocalDate productionDateEnd,
                               String farmerName,
                               Long farmerId,
                               Long representativeOfRecipientUserCustomerId) {
        this.companyId = companyId;
        this.purchaseId = purchaseId;
        this.preferredWayOfPayment = preferredWayOfPayment;
        this.paymentStatus = paymentStatus;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
        this.farmerName = farmerName;
        this.farmerId = farmerId;
        this.representativeOfRecipientUserCustomerId = representativeOfRecipientUserCustomerId;
    }

    public Long companyId;
    public Long purchaseId; // StockOrder.id
    public PreferredWayOfPayment preferredWayOfPayment;
    public PaymentStatus paymentStatus;
    public LocalDate productionDateStart;
    public LocalDate productionDateEnd;
    public String farmerName; // RepresentativeOfRecipientUserCustomer.name
    public Long farmerId;
    public Long representativeOfRecipientUserCustomerId;

    @Override
    public String toString() {
        return "PaymentQueryRequest{" +
                "companyId=" + companyId +
                ", purchaseId=" + purchaseId +
                ", preferredWayOfPayment=" + preferredWayOfPayment +
                ", paymentStatus=" + paymentStatus +
                ", productionDateStart=" + productionDateStart +
                ", productionDateEnd=" + productionDateEnd +
                ", farmerName='" + farmerName + '\'' +
                ", farmerId='" + farmerId + '\'' +
                ", representativeOfRecipientUserCustomerId='" + representativeOfRecipientUserCustomerId + '\'' +
                '}';
    }
}
