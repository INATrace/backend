package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.stockorder.StockOrderService;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.tools.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Lazy
@Service
public class TransactionService extends BaseService {

    @Autowired
    private StockOrderService stockOrderService;

    public ApiTransaction getApiTransaction(Long id) throws ApiException {
        return TransactionMapper.toApiTransaction(fetchEntity(id, Transaction.class));
    }

    /**
     * Creates new or updates existing Transaction.
     *
     * @param apiTransaction - API transaction request
     * @param isProcessing - Is transaction part of ProcessingAction with type PROCESSING?
     * @param targetStockOrderId - Target StockOrder ID (only applies for ProcessingAction with type TRANSFER)
     * @return Inserted transactions
     * @throws ApiException - When something goes wrong..
     */
    @Transactional
    public ApiBaseEntity createOrUpdateTransaction(ApiTransaction apiTransaction,
                                                    Boolean isProcessing,
                                                    Long targetStockOrderId) throws ApiException {

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
//        transaction.setTargetProcessingOrder(entity);

        // If ProcessingActionType is TRANSFER, set target StockOrder
        if (!transaction.getIsProcessing()) {
            StockOrder targetStockOrder = fetchEntity(targetStockOrderId, StockOrder.class);
            transaction.setTargetStockOrder(targetStockOrder);
            transaction.setOutputMeasureUnitType(targetStockOrder.getMeasurementUnitType());
            transaction.setTargetFacility(targetStockOrder.getFacility());

        } else {
            transaction.setOutputMeasureUnitType(transaction.getInputMeasureUnitType());
            transaction.setTargetFacility(transaction.getSourceFacility()); // TODO: As in nodeJS code, this should be fetched from ProcessingOrder which does not have a Facility?
        }

        if (!transaction.getIsProcessing() && (transaction.getSemiProduct() == null || !transaction.getSemiProduct().equals(transaction.getSourceStockOrder().getSemiProduct())))
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Target SemiProduct has to match source SemiProduct.");

        if (transaction.getId() == null)
            em.persist(transaction);

        return new ApiBaseEntity(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id, Long userId) throws ApiException {

        Transaction transaction = fetchEntity(id, Transaction.class);

        StockOrder sourceStockOrder = transaction.getSourceStockOrder();
        StockOrder targetStockOrder = transaction.getTargetStockOrder();

        // Set source StockOrder available quantity
        if (sourceStockOrder != null) {
            sourceStockOrder.setAvailableQuantity(Math.min(
                    sourceStockOrder.getAvailableQuantity() + transaction.getInputQuantity(),
                    sourceStockOrder.getTotalQuantity()
            ));
            stockOrderService.createOrUpdateStockOrder(StockOrderMapper.toApiStockOrder(sourceStockOrder, userId), userId);
        }

        // Set target StockOrder fulfilled quantity
        if (!transaction.getIsProcessing() && targetStockOrder != null) {
            targetStockOrder.setFulfilledQuantity(Math.max(
                    targetStockOrder.getFulfilledQuantity() - transaction.getOutputQuantity(),
                    0
            ));
            stockOrderService.createOrUpdateStockOrder(StockOrderMapper.toApiStockOrder(targetStockOrder, userId), userId);
        }

        em.remove(transaction);
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
