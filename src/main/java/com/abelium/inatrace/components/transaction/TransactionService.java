package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.tools.Queries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Lazy
@Service
public class TransactionService extends BaseService {

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
    public void deleteTransaction(Long id) throws ApiException {
        em.remove(fetchEntity(id, Transaction.class));
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
