package com.abelium.inatrace.components.processingorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiProcessingOrder extends ApiBaseEntity {

    @Schema(description = "Initiator user ID")
    public Long initiatorUserId;

    @Schema(description = "Timestamp indicates when processing order have been created")
    private Instant creationTimestamp;

    @Schema(description = "Processing action")
    public ApiProcessingAction processingAction;

    @Schema(description = "Processing date")
    public LocalDate processingDate;

    @Schema(description = "Input transactions")
    public List<ApiTransaction> inputTransactions;

    @Schema(description = "Target stock orders")
    public List<ApiStockOrder> targetStockOrders;

    public Long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(Long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public ApiProcessingAction getProcessingAction() {
        return processingAction;
    }

    public void setProcessingAction(ApiProcessingAction processingAction) {
        this.processingAction = processingAction;
    }

    public LocalDate getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(LocalDate processingDate) {
        this.processingDate = processingDate;
    }

    public List<ApiTransaction> getInputTransactions() {
        if (inputTransactions == null) {
            inputTransactions = new ArrayList<>();
        }
        return inputTransactions;
    }

    public void setInputTransactions(List<ApiTransaction> inputTransactions) {
        this.inputTransactions = inputTransactions;
    }

    public List<ApiStockOrder> getTargetStockOrders() {
        if (targetStockOrders == null) {
            targetStockOrders = new ArrayList<>();
        }
        return targetStockOrders;
    }

    public void setTargetStockOrders(List<ApiStockOrder> targetStockOrders) {
        this.targetStockOrders = targetStockOrders;
    }
}
