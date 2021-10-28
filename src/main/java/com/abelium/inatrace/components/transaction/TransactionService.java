package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.stockorder.StockOrderService;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Lazy
@Service
public class TransactionService extends BaseService {

    @Autowired
    private StockOrderService stockOrderService;

    public ApiTransaction getApiTransaction(Long id, Language language) throws ApiException {
        return TransactionMapper.toApiTransaction(fetchEntity(id, Transaction.class), language);
    }

    /**
     * Return a ist of input transactions for provided stock order ID. Used in Quote orders.
     * @param stockOrderId Stock order ID.
     * @return List of transacitons.
     */
    public ApiPaginatedList<ApiTransaction> getStockOrderInputTransactions(Long stockOrderId) throws ApiException {

        // Fetch the stock order
        StockOrder stockOrder = stockOrderService.fetchEntity(stockOrderId, StockOrder.class);

        // Validate that stock order has processing order (if not proccessing order, there are no transactions)
        ProcessingOrder processingOrder = stockOrder.getProcessingOrder();
        if (processingOrder == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "The Stock order with the provided ID has no Processing order");
        }

        return new ApiPaginatedList<>(
                processingOrder.getInputTransactions().stream().map(TransactionMapper::toApiTransactionBase)
                        .collect(Collectors.toList()), processingOrder.getInputTransactions().size());
    }

    /**
     * Creates new or updates existing Transaction.
     *
     * @param apiTransaction - API transaction request
     * @param isProcessing - Is transaction part of ProcessingAction with type PROCESSING?
     * @return Inserted transactions
     * @throws ApiException - When something goes wrong..
     */
    @Transactional
    public ApiBaseEntity createOrUpdateTransaction(ApiTransaction apiTransaction,
                                                   Boolean isProcessing) throws ApiException {

        Transaction transaction = apiTransaction.getId() != null
                ? fetchEntity(apiTransaction.getId(), Transaction.class)
                : new Transaction();

        transaction.setCompany(fetchEntity(apiTransaction.getCompany().getId(), Company.class));
        transaction.setInitiationUserId(apiTransaction.getInitiationUserId());
        transaction.setStatus(apiTransaction.getStatus());
        transaction.setIsProcessing(isProcessing);
        transaction.setInputQuantity(apiTransaction.getInputQuantity());
        transaction.setOutputQuantity(apiTransaction.getOutputQuantity());
        transaction.setSourceStockOrder(fetchEntity(apiTransaction.getSourceStockOrder().getId(), StockOrder.class));
        transaction.setSourceFacility(transaction.getSourceStockOrder().getFacility());
        transaction.setInputMeasureUnitType(transaction.getSourceStockOrder().getMeasurementUnitType());
        transaction.setSemiProduct(transaction.getSourceStockOrder().getSemiProduct());
        transaction.setOutputMeasureUnitType(transaction.getInputMeasureUnitType());

        if (!transaction.getIsProcessing() && (transaction.getSemiProduct() == null || !transaction.getSemiProduct().equals(transaction.getSourceStockOrder().getSemiProduct())))
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Target SemiProduct has to match source SemiProduct.");

        if (transaction.getId() == null)
            em.persist(transaction);

        return new ApiBaseEntity(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id, Long userId, Language language) throws ApiException {

        Transaction transaction = fetchEntity(id, Transaction.class);

        revertQuantities(transaction, userId, language);

        // Only PENDING transactions can be deleted within QUOTE order
        if (transaction.getStatus() != TransactionStatus.PENDING
                && transaction.getTargetProcessingOrder() != null
                && transaction.getTargetProcessingOrder().getProcessingAction().getType() == ProcessingActionType.SHIPMENT) {

            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Only PENDING transactions can be deleted.");
        }

        em.remove(transaction);
    }

    @Transactional
    public void approveTransaction(Long id, Long userId, Language language) throws ApiException {
        Transaction transaction = fetchEntity(id, Transaction.class);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Only PENDING transactions can be approved.");
        }

        transaction.setStatus(TransactionStatus.EXECUTED);

        // Update quote order (to refresh quantities)
        if (transaction.getTargetProcessingOrder() != null && !transaction.getTargetProcessingOrder().getTargetStockOrders().isEmpty()) {

            ProcessingOrder processingOrder = transaction.getTargetProcessingOrder();
            StockOrder quoteStockOrder = processingOrder.getTargetStockOrders().get(0);
            quoteStockOrder.setAvailableQuantity(quoteStockOrder.getAvailableQuantity().add(transaction.getInputQuantity()));
            stockOrderService.createOrUpdateStockOrder(
                    StockOrderMapper.toApiStockOrder(quoteStockOrder, userId, language),
                    userId,
                    processingOrder
            );
        }
    }

    @Transactional
    public void rejectTransaction(ApiTransaction apiTransaction, Long userId, Language language) throws ApiException {

        Transaction transaction = fetchEntity(apiTransaction.getId(), Transaction.class);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Only PENDING transactions can be rejected.");
        }
        if (apiTransaction.getRejectComment() == null || apiTransaction.getRejectComment().isEmpty()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Reject comment cannot be null.");
        }

        transaction.setStatus(TransactionStatus.CANCELED);
        transaction.setRejectComment(apiTransaction.getRejectComment());

        // Revert quantities but do NOT delete the transaction!
        revertQuantities(transaction, userId, language);

        // Balance fulfilled quantity in quote stock order
        if (transaction.getTargetProcessingOrder() != null && !transaction.getTargetProcessingOrder().getTargetStockOrders().isEmpty()) {
            ProcessingOrder processingOrder = transaction.getTargetProcessingOrder();
            StockOrder quoteStockOrder = processingOrder.getTargetStockOrders().get(0);
            stockOrderService.createOrUpdateStockOrder(
                    StockOrderMapper.toApiStockOrder(quoteStockOrder, userId, language),
                    userId,
                    processingOrder
            );
        }
    }

    private void revertQuantities(Transaction transaction, Long userId, Language language) throws ApiException {

        StockOrder sourceStockOrder = transaction.getSourceStockOrder();
        StockOrder targetStockOrder = transaction.getTargetStockOrder();

        // Set source StockOrder available quantity
        if (sourceStockOrder != null) {
            sourceStockOrder.setAvailableQuantity(sourceStockOrder.getAvailableQuantity().add(transaction.getInputQuantity()));
            stockOrderService.createOrUpdateStockOrder(
                    StockOrderMapper.toApiStockOrder(sourceStockOrder, userId, language),
                    userId,
                    null
            );
        }

        // Set target StockOrder fulfilled quantity
        if (!transaction.getIsProcessing() && targetStockOrder != null) {
            targetStockOrder.setFulfilledQuantity(targetStockOrder.getFulfilledQuantity().subtract(transaction.getOutputQuantity()));
            stockOrderService.createOrUpdateStockOrder(
                    StockOrderMapper.toApiStockOrder(targetStockOrder, userId, language),
                    userId,
                    null
            );
        }
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
