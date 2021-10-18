package com.abelium.inatrace.components.processingorder.mappers;

import com.abelium.inatrace.components.processingaction.ProcessingActionMapper;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;

import java.util.stream.Collectors;

public class ProcessingOrderMapper {

    public static ApiProcessingOrder toApiProcessingOrder(ProcessingOrder entity){
        if (entity == null) return null;
        ApiProcessingOrder apiProcessingOrder = new ApiProcessingOrder();
        apiProcessingOrder.setId(entity.getId());
        apiProcessingOrder.setInitiatorUserId(entity.getInitiatorUserId());
        apiProcessingOrder.setProcessingDate(entity.getProcessingDate());
        apiProcessingOrder.setProcessingAction(ProcessingActionMapper.toApiProcessingAction(entity.getProcessingAction()));
        apiProcessingOrder.setInputTransactions(entity.getInputTransactions().stream().map(TransactionMapper::toApiTransaction).collect(Collectors.toList()));
        apiProcessingOrder.setTargetStockOrders(entity.getTargetStockOrders().stream().map(so -> StockOrderMapper.toApiStockOrder(so ,null)).collect(Collectors.toList()));
        return apiProcessingOrder;
    }

}