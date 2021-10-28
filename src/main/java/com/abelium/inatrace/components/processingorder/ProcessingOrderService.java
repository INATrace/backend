package com.abelium.inatrace.components.processingorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.processingorder.mappers.ProcessingOrderMapper;
import com.abelium.inatrace.components.stockorder.StockOrderService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.transaction.TransactionService;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Service
public class ProcessingOrderService extends BaseService {

    @Autowired
    private StockOrderService stockOrderService;

    @Autowired
    private TransactionService transactionService;

    public ApiProcessingOrder getProcessingOrder(Long id, Language language) throws ApiException {
        return ProcessingOrderMapper.toApiProcessingOrder(fetchEntity(id, ProcessingOrder.class), language);
    }

    public ApiPaginatedList<ApiProcessingOrder> getProcessingOrderList(ApiPaginatedRequest request, Language language) {
        return PaginationTools.createPaginatedResponse(em, request,
                () -> processingOrderQueryObject(request), processingOrder -> ProcessingOrderMapper.toApiProcessingOrder(processingOrder, language));
    }

    private ProcessingOrder processingOrderQueryObject(ApiPaginatedRequest request) {

        ProcessingOrder processingOrderProxy = Torpedo.from(ProcessingOrder.class);
        QueryTools.orderBy(request.sort, processingOrderProxy.getId());

        return processingOrderProxy;
    }

    /**
     * This is temporary method so that everything else stays backward compatible.
     * @param apiProcessingOrder - Processing order request
     * @param userId - ID of the user that has requested this action
     * @return Status
     * @throws ApiException - You know... when something goes wrong it's nice to have a feedback
     */
    @Transactional
    public ApiBaseEntity createOrUpdateProcessingOrder(ApiProcessingOrder apiProcessingOrder, Long userId, Language language) throws ApiException {

        ProcessingOrder entity = fetchEntityOrElse(apiProcessingOrder.getId(), ProcessingOrder.class, new ProcessingOrder());

        // Custom exceptions for required fields
        if (apiProcessingOrder.getProcessingAction() == null ||
                apiProcessingOrder.getProcessingAction().getId() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Processing action ID must be provided!");
        }

        if (apiProcessingOrder.getTargetStockOrders() == null || apiProcessingOrder.getTargetStockOrders().isEmpty()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one target StockOrder has to be provided!");
        }

        ProcessingAction processingAction = fetchEntity(apiProcessingOrder.getProcessingAction().getId(), ProcessingAction.class);

        entity.setProcessingAction(processingAction);
        entity.setInitiatorUserId(apiProcessingOrder.getInitiatorUserId());
        entity.setProcessingDate(apiProcessingOrder.getProcessingDate() != null
                ? apiProcessingOrder.getProcessingDate()
                : Instant.now());

        /*
         * --- VALIDATION ---
         */

        // Various validations based on ProcessingActionType
        switch (processingAction.getType()) {

            // SHIPMENT is actually QUOTE
            case SHIPMENT:
                return createOrUpdateQuoteOrder(entity, apiProcessingOrder, userId, language);

            case PROCESSING:

                if (apiProcessingOrder.getInputTransactions() == null || apiProcessingOrder.getInputTransactions().isEmpty()) {
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one input Transaction has to be provided!");
                }

                // Validate that there is no transaction that is referencing current ProcessingOrder (via targetStockOrder)
                // That kind of transaction cannot be part of the same processing
                if (entity.getId() != null) {
                    for (ApiTransaction apiTransaction : apiProcessingOrder.getInputTransactions()) {

                        if (apiTransaction.getId() != null
                                && apiTransaction.getTargetStockOrder() != null
                                && entity.getId().equals(apiTransaction.getTargetStockOrder().getId()))

                            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Transaction with ID '"
                                    + apiTransaction.getId()
                                    + "' cannot be used, because it has been already used in this processing step");
                    }
                }
                break;

            case TRANSFER:

                if (apiProcessingOrder.getInputTransactions() == null || apiProcessingOrder.getInputTransactions().isEmpty()) {
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one input Transaction has to be provided!");
                }

                // When processing action is of type TRANSFER, source StockOrders (contained within each input Transaction)
                // will be mapped to target StockOrders, so their size should be the same.
                if (apiProcessingOrder.getInputTransactions().size() != apiProcessingOrder.getTargetStockOrders().size())
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "When ProcessingActionType is 'TRANSFER', " +
                            "size of 'inputTransactions' has to match the size of 'targetStockOrders'!");

                // Source StockOrders from inputTransactions must match with targetStockOrders.
                // Target StockOrders represent new/modified source StockOrder.
                for (int i = 0; i < apiProcessingOrder.getInputTransactions().size(); i++) {

                    ApiStockOrder targetStockOrder = apiProcessingOrder.getTargetStockOrders().get(i);
                    ApiStockOrder sourceStockOrder = apiProcessingOrder.getInputTransactions().get(i).getSourceStockOrder();

                    // Each input Transaction must contain a source StockOrder
                    if (sourceStockOrder == null || sourceStockOrder.getId() == null)
                        throw new ApiException(ApiStatus.VALIDATION_ERROR,
                                "Each input Transaction must contain a valid source StockOrder.");

                    // If target StockOrder contains ID, then there must be matching source StockOrder
                    if (targetStockOrder.getId() != null && !targetStockOrder.getId().equals(sourceStockOrder.getId()))
                        throw new ApiException(ApiStatus.VALIDATION_ERROR,
                                "For each existing target StockOrder there has to be a source StockOrder with the same ID.");
                }
                break;

            default:
                throw new ApiException(ApiStatus.ERROR, "Selected processing action is missing a ProcessingActionType!");
        }

        // Validate transactions. They should all contain:
        // - Company ID
        // - Source StockOrder ID
        // - TransactionStatus
        boolean areTransactionsValid = apiProcessingOrder.getInputTransactions()
                .stream()
                .allMatch(it -> it.getCompany() != null
                        && it.getCompany().getId() != null
                        && it.getSourceStockOrder() != null
                        && it.getSourceStockOrder().getId() != null
                        && it.getStatus() != null
                );

        if (!areTransactionsValid) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "At least one of the provided transactions is not valid!");
        }

        // Delete target StockOrder and input Transactions that are not present in the request
        // (applies only for existing ProcessingOrders)
        if(entity.getId() != null) {

            // Remove transactions that are not present in request
            List<Transaction> transactionsToBeDeleted = entity.getInputTransactions()
                    .stream()
                    .filter(transaction -> apiProcessingOrder.getInputTransactions()
                            .stream()
                            .noneMatch(apiTransaction -> transaction.getId().equals(apiTransaction.getId())))
                    .collect(Collectors.toList());

            for (Transaction t: transactionsToBeDeleted)
                transactionService.deleteTransaction(t.getId(), userId, language);

            // Find target StockOrders that are not present in request
            List<StockOrder> targetStockOrdersToBeDeleted = entity.getTargetStockOrders()
                    .stream()
                    .filter(targetStockOrder -> apiProcessingOrder.getTargetStockOrders()
                            .stream()
                            .noneMatch(apiTargetStockOrder -> targetStockOrder.getId().equals(apiTargetStockOrder.getId())))
                    .collect(Collectors.toList());

            // Before deleting, verify that deletion is possible
            for (StockOrder targetStockOrder : targetStockOrdersToBeDeleted) {

                // Used quantity cannot be negative: "fulfilledQuantity - availableQuantity >= 0"
                if (targetStockOrder.getTotalQuantity().subtract(targetStockOrder.getAvailableQuantity())
                        .compareTo(BigDecimal.ZERO) < 0)
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Target StockOrder with ID '"
                            + targetStockOrder.getId() + "' cannot be deleted!");
            }

            entity.getTargetStockOrders().removeAll(targetStockOrdersToBeDeleted);
        }

        // Verify that existing target StockOrders (in other words those with ID) can be updated
        for (StockOrder targetStockOrder : entity.getTargetStockOrders()) {
            ApiStockOrder apiTargetStockOrder = apiProcessingOrder.getTargetStockOrders()
                    .stream()
                    .filter(apiTSO -> targetStockOrder.getId().equals(apiTSO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ApiException(ApiStatus.ERROR,
                            "Inappropriate deletion of target StockOrders that are not present in request!"));

            // Total quantity - used quantity >= 0
            if (apiTargetStockOrder.getTotalQuantity().subtract(targetStockOrder.getTotalQuantity())
                    .compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal usedQuantity = apiTargetStockOrder.getFulfilledQuantity()
                        .subtract(apiTargetStockOrder.getAvailableQuantity());
                if (targetStockOrder.getTotalQuantity().compareTo(usedQuantity) < 0)
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Cannot reduce total quantity below already used quantity.");
            }
        }

        // Create new or update existing input Transactions
        for (int i = 0; i < apiProcessingOrder.getInputTransactions().size(); i++) {

            ApiTransaction apiTransaction = apiProcessingOrder.getInputTransactions().get(i);
            Boolean isProcessing = processingAction.getType() == ProcessingActionType.PROCESSING;

            ApiBaseEntity insertedApiTransaction = transactionService.createOrUpdateTransaction(apiTransaction, isProcessing);
            Transaction insertedTransaction = fetchEntity(insertedApiTransaction.getId(), Transaction.class);
            insertedTransaction.setTargetProcessingOrder(entity);
            entity.getInputTransactions().add(insertedTransaction);

            // Update source StockOrder
            stockOrderService.createOrUpdateStockOrder(apiTransaction.getSourceStockOrder(), userId, entity);

            // Set targetStockOrders for TRANSFER
            if (processingAction.getType() == ProcessingActionType.TRANSFER) {
                Long targetStockOrderId = stockOrderService.createOrUpdateStockOrder(apiProcessingOrder.getTargetStockOrders().get(i), userId, entity).getId();
                StockOrder targetStockOrder = fetchEntity(targetStockOrderId, StockOrder.class);
                targetStockOrder.setProcessingOrder(entity);

                insertedTransaction.setOutputMeasureUnitType(targetStockOrder.getMeasurementUnitType());
                insertedTransaction.setTargetFacility(targetStockOrder.getFacility());
                insertedTransaction.setTargetStockOrder(targetStockOrder);
                entity.getTargetStockOrders().add(targetStockOrder);
            }
        }

        // Create new or update existing target for PROCESSING
        if (processingAction.getType() != ProcessingActionType.TRANSFER) {

            for (ApiStockOrder apiTargetStockOrder: apiProcessingOrder.getTargetStockOrders()) {
                Long insertedTargetStockOrderId = stockOrderService.createOrUpdateStockOrder(apiTargetStockOrder, userId, entity).getId();
                StockOrder targetStockOrder = fetchEntity(insertedTargetStockOrderId, StockOrder.class);
                targetStockOrder.setProcessingOrder(entity);
                entity.getTargetStockOrders().add(targetStockOrder);
            }

            // Set target facility to transactions
            for (Transaction t : entity.getInputTransactions()) {
                t.setTargetFacility(entity.getTargetStockOrders().get(0).getFacility());
            }
        }

        if (entity.getId() == null) {
            em.persist(entity);
        }

        return new ApiBaseEntity(entity);
    }

    @Transactional
    public ApiBaseEntity createOrUpdateQuoteOrder(ProcessingOrder entity, ApiProcessingOrder apiProcessingOrder, Long userId, Language language) throws ApiException {

        if (apiProcessingOrder.getTargetStockOrders().size() < 1) {
            throw new ApiException(ApiStatus.INVALID_REQUEST,
                    "At least one target StockOrder should be present in the request.");
        }

        // Remove transactions that are not present in request
        if (entity.getId() != null) {
            List<Transaction> transactionsToBeDeleted = entity.getInputTransactions()
                    .stream()
                    .filter(transaction -> apiProcessingOrder.getInputTransactions()
                            .stream()
                            .noneMatch(apiTransaction -> transaction.getId().equals(apiTransaction.getId())))
                    .collect(Collectors.toList());


            entity.getInputTransactions().removeAll(transactionsToBeDeleted);
            for (Transaction t: transactionsToBeDeleted) {
                transactionService.deleteTransaction(t.getId(), userId, language);
            }

        }

        // Create new or update existing input Transactions
        for (int i = 0; i < apiProcessingOrder.getInputTransactions().size(); i++) {

            ApiTransaction apiTransaction = apiProcessingOrder.getInputTransactions().get(i);
            Boolean isProcessing = entity.getProcessingAction().getType() == ProcessingActionType.PROCESSING;

            Long insertedTransactionId = transactionService.createOrUpdateTransaction(apiTransaction, isProcessing).getId();
            Transaction insertedTransaction = fetchEntity(insertedTransactionId, Transaction.class);

            entity.getInputTransactions().remove(insertedTransaction);
            entity.getInputTransactions().add(insertedTransaction);
            insertedTransaction.setTargetProcessingOrder(entity);

            // Update source StockOrder
            if (apiTransaction.getId() == null) {
                stockOrderService.createOrUpdateStockOrder(apiTransaction.getSourceStockOrder(), userId, entity);
            }

        }

        ApiStockOrder apiQuoteStockOrder = apiProcessingOrder.getTargetStockOrders().get(0);

        if (apiQuoteStockOrder.getOrderType() != OrderType.GENERAL_ORDER) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Order must be of orderType " +  OrderType.GENERAL_ORDER
                    + " to allow input transactions");
        }

        if (apiQuoteStockOrder.getId() == null) {
            if (apiQuoteStockOrder.getFulfilledQuantity() != null && apiQuoteStockOrder.getFulfilledQuantity().compareTo(BigDecimal.ZERO) != 0) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Fulfilled quantity must be 0");
            }
        }

        Long insertedStockOrderId = stockOrderService.createOrUpdateStockOrder(apiQuoteStockOrder, userId, entity).getId();
        StockOrder quoteStockOrder = fetchEntity(insertedStockOrderId, StockOrder.class);

        quoteStockOrder.setProcessingOrder(entity);
        entity.setTargetStockOrders(List.of(quoteStockOrder));

        if (entity.getId() == null) {
            em.persist(entity);
        }

        return new ApiBaseEntity(entity);
    }

    @Transactional
    public void deleteProcessingOrder(Long id) throws ApiException {

        // Transactions should not be deleted -> May result in inappropriate quantities

        ProcessingOrder entity = fetchEntity(id, ProcessingOrder.class);

        // Manually detach all related transactions
        for (Transaction t: entity.getInputTransactions()) {
            t.setTargetProcessingOrder(null);
        }

        // Detach target stock orders
        for (StockOrder so: entity.getTargetStockOrders()) {
            so.setProcessingOrder(null);
        }

        em.remove(entity);
    }

    private <E> E fetchEntity(Long id, Class<E> entityClass) throws ApiException {

        E entity = Queries.get(em, entityClass, id);
        if (entity == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST,
                    entityClass.getSimpleName() + " entity with ID '" + id  + "' not found.");
        }
        return entity;
    }

    private <E> E fetchEntityOrElse(Long id, Class<E> entityClass, E defaultValue) {
        E entity = Queries.get(em, entityClass, id);
        return entity == null ? defaultValue : entity;
    }

}
