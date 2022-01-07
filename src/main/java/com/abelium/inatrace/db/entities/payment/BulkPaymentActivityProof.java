package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.ActivityProof;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BulkPaymentActivityProof extends BaseEntity{

    @ManyToOne
    private BulkPayment bulkPayment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ActivityProof activityProof;

    public BulkPayment getBulkPayment() {
        return bulkPayment;
    }

    public void setBulkPayment(BulkPayment bulkPayment) {
        this.bulkPayment = bulkPayment;
    }

    public ActivityProof getActivityProof() {
        return activityProof;
    }

    public void setActivityProof(ActivityProof activityProof) {
        this.activityProof = activityProof;
    }
}
