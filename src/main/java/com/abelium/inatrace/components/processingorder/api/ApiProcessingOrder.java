package com.abelium.inatrace.components.processingorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.components.stockorder.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.List;

public class ApiProcessingOrder extends ApiBaseEntity {

    @ApiModelProperty(value = "Initiator user ID")
    public Long initiatorUserId;

    @ApiModelProperty(value = "Processing action")
    public ApiProcessingAction processingAction;

    @ApiModelProperty(value = "Processing date")
    @JsonSerialize(converter = SimpleDateConverter.Serialize.class)
    @JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    public Instant processingDate;

    @ApiModelProperty(value = "Input transactions")
    public List<ApiTransaction> inputTransactions;

    @ApiModelProperty(value = "Target stock orders")
    public List<ApiStockOrder> targetStockOrders;

    public Long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(Long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public ApiProcessingAction getProcessingAction() {
        return processingAction;
    }

    public void setProcessingAction(ApiProcessingAction processingAction) {
        this.processingAction = processingAction;
    }

    public Instant getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Instant processingDate) {
        this.processingDate = processingDate;
    }

    public List<ApiTransaction> getInputTransactions() {
        return inputTransactions;
    }

    public void setInputTransactions(List<ApiTransaction> inputTransactions) {
        this.inputTransactions = inputTransactions;
    }

    public List<ApiStockOrder> getTargetStockOrders() {
        return targetStockOrders;
    }

    public void setTargetStockOrders(List<ApiStockOrder> targetStockOrders) {
        this.targetStockOrders = targetStockOrders;
    }
}
