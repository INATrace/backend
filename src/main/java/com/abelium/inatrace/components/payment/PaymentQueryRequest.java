package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.Instant;

public class PaymentQueryRequest {

    public PaymentQueryRequest() { }

    public PaymentQueryRequest(Long companyId) {
        this.companyId = companyId;
    }

    public PaymentQueryRequest(Long companyId,
                               Long purchaseId,
                               PreferredWayOfPayment preferredWayOfPayment,
                               PaymentStatus paymentStatus,
                               Instant productionDateStart,
                               Instant productionDateEnd,
                               String farmerName,
                               Long farmerId) {
        this.companyId = companyId;
        this.purchaseId = purchaseId;
        this.preferredWayOfPayment = preferredWayOfPayment;
        this.paymentStatus = paymentStatus;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
        this.farmerName = farmerName;
        this.farmerId = farmerId;
    }

    public Long companyId;
    public Long purchaseId; // StockOrder.id
    public PreferredWayOfPayment preferredWayOfPayment;
    public PaymentStatus paymentStatus;
    public Instant productionDateStart;
    public Instant productionDateEnd;
    public String farmerName; // RepresentativeOfRecipientUserCustomer.name
    public Long farmerId;

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
                '}';
    }
}
