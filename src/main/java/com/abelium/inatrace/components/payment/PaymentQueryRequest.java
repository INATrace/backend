package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.Instant;

public class PaymentQueryRequest {

    PaymentQueryRequest() { }

    PaymentQueryRequest(PreferredWayOfPayment preferredWayOfPayment,
                        PaymentStatus paymentStatus,
                        Instant productionDateStart,
                        Instant productionDateEnd,
                        String farmerName) {
        this.preferredWayOfPayment = preferredWayOfPayment;
        this.paymentStatus = paymentStatus;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
        this.farmerName = farmerName;
    }

    public PreferredWayOfPayment preferredWayOfPayment;
    public PaymentStatus paymentStatus;
    public Instant productionDateStart;
    public Instant productionDateEnd;
    // representativeOfRecipientUserCustomer.name
    public String farmerName;
}
