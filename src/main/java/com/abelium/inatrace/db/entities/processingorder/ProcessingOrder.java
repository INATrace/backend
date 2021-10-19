package com.abelium.inatrace.db.entities.processingorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProcessingOrder extends TimestampEntity {

    @Version
    private Long entityVersion;

    @Column
    private Long initiatorUserId;

    @Column
    private Instant processingDate;

    @ManyToOne
    private ProcessingAction processingAction;

    @OneToMany(mappedBy = "targetProcessingOrder")
    private List<Transaction> inputTransactions;

    @OneToMany(mappedBy = "processingOrder")
    private List<StockOrder> targetStockOrders;

    public Long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(Long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public Instant getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Instant processingDate) {
        this.processingDate = processingDate;
    }

    public ProcessingAction getProcessingAction() {
        return processingAction;
    }

    public void setProcessingAction(ProcessingAction processingAction) {
        this.processingAction = processingAction;
    }

    public List<Transaction> getInputTransactions() {
        if (inputTransactions == null)
            inputTransactions = new ArrayList<>();
        return inputTransactions;
    }

    public void setInputTransactions(List<Transaction> inputTransactions) {
        this.inputTransactions = inputTransactions;
    }

    public List<StockOrder> getTargetStockOrders() {
        if (targetStockOrders == null)
            targetStockOrders = new ArrayList<>();
        return targetStockOrders;
    }

    public void setTargetStockOrders(List<StockOrder> targetStockOrders) {
        this.targetStockOrders = targetStockOrders;
    }
}
