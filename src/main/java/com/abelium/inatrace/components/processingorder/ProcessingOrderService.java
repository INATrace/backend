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
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.types.ProcessingActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Service
public class ProcessingOrderService extends BaseService {

    @Autowired
    private StockOrderService stockOrderService;

    public ApiProcessingOrder getProcessingOrder(long id) throws ApiException {
        return ProcessingOrderMapper.toApiProcessingOrder(fetchEntity(id, ProcessingOrder.class));
    }

    public ApiPaginatedList<ApiProcessingOrder> getProcessingOrderList(ApiPaginatedRequest request) {
        return PaginationTools.createPaginatedResponse(em, request,
                () -> processingOrderQueryObject(request), ProcessingOrderMapper::toApiProcessingOrder);
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
    public ApiBaseEntity createOrUpdateProcessingOrder(ApiProcessingOrder apiProcessingOrder, Long userId) throws ApiException {

        ProcessingOrder entity = fetchEntityOrElse(apiProcessingOrder.getId(), ProcessingOrder.class, new ProcessingOrder());

        // Custom exceptions for required fields
        if(apiProcessingOrder.getProcessingAction() == null || apiProcessingOrder.getProcessingAction().getId() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Processing action ID must be provided!");
        if(apiProcessingOrder.getInputTransactions() == null || apiProcessingOrder.getInputTransactions().isEmpty())
            throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one input Transaction has to be provided!");
        if(apiProcessingOrder.getTargetStockOrders() == null || apiProcessingOrder.getTargetStockOrders().isEmpty())
            throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one target StockOrder has to be provided!");

        ProcessingAction processingAction = fetchEntity(apiProcessingOrder.getProcessingAction().getId(), ProcessingAction.class);

        entity.setProcessingAction(processingAction);
        entity.setInitiatorUserId(apiProcessingOrder.getInitiatorUserId()); // TODO: Should this be set to the user who has initiated the request?
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
                if (apiProcessingOrder.getTargetStockOrders().size() < 1)
                    throw new ApiException(ApiStatus.INVALID_REQUEST,
                            "At least one target StockOrder should be present in the request.");

//                ApiStockOrder targetStockOrder = apiProcessingOrder.getTargetStockOrders().get(0);
                // targetStockOrder.setProcessingOrder(...); / .setProcessingOrderId(...);
                // targetStockOrder.setProcessingAction(...); / .setProcessingActionId(...);

                break;

            case PROCESSING:
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

        if(!areTransactionsValid){
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "At least one of the provided transactions is not valid!");
        }

        // Delete target StockOrder and input Transactions that are not present in the request
        // (applies only for existing ProcessingOrders)
        if(entity.getId() != null) {

            // Remove transactions that are not present in request
            entity.getInputTransactions().removeAll(entity.getInputTransactions()
                    .stream()
                    .filter(transaction -> apiProcessingOrder.getInputTransactions()
                            .stream()
                            .noneMatch(apiTransaction -> transaction.getId().equals(apiTransaction.getId())))
                    .collect(Collectors.toList()));

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
                if (targetStockOrder.getTotalQuantity() - targetStockOrder.getAvailableQuantity() < 0)
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

            // TODO: Explain validation??? Is it even necessary???
            if (targetStockOrder.getTotalQuantity() - apiTargetStockOrder.getTotalQuantity() < 0) {
                Integer usedQuantity = targetStockOrder.getFulfilledQuantity() - targetStockOrder.getAvailableQuantity();
                if(targetStockOrder.getTotalQuantity() < usedQuantity)
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Cannot delete used stock order.");
            }
        }

        // List in which targetStockOrderIds are inserted in same sequence as they are received in JSON
        List<Long> sequentialIdsOfInsertedTargetStockOrders = new ArrayList<>();

        // Create new or update existing target StockOrders
        for (ApiStockOrder apiStockOrder: apiProcessingOrder.getTargetStockOrders()) {
            Long insertedTargetStockOrderId = stockOrderService.createOrUpdateStockOrder(apiStockOrder, userId).getId();
            StockOrder insertedTargetStockOrder = fetchEntity(insertedTargetStockOrderId, StockOrder.class);
            entity.getTargetStockOrders().add(insertedTargetStockOrder);
            sequentialIdsOfInsertedTargetStockOrders.add(insertedTargetStockOrderId);
        }

        // Create new or update existing input Transactions
        for (int i = 0; i < apiProcessingOrder.getInputTransactions().size(); i++) {
            ApiTransaction it = apiProcessingOrder.getInputTransactions().get(i);

            // No need to fetch transaction, as it cannot exist yet
            Transaction transaction = new Transaction();
            transaction.setCompany(fetchEntity(it.getCompany().getId(), Company.class));
            transaction.setInitiationUserId(it.getInitiationUserId());
            transaction.setStatus(it.getStatus());
            transaction.setIsProcessing(processingAction.getType() == ProcessingActionType.PROCESSING);
            transaction.setInputQuantity(it.getInputQuantity());
            transaction.setOutputQuantity(it.getOutputQuantity());
            transaction.setSourceStockOrder(fetchEntity(it.getSourceStockOrder().getId(), StockOrder.class));
            transaction.setSourceFacility(transaction.getSourceStockOrder().getFacility());
            transaction.setInputMeasureUnitType(transaction.getSourceStockOrder().getMeasurementUnitType());
            transaction.setSemiProduct(transaction.getSourceStockOrder().getSemiProduct());
            transaction.setTargetProcessingOrder(entity);

            // If ProcessingActionType is TRANSFER, set target StockOrder
            if (!transaction.getIsProcessing()) {
                StockOrder targetStockOrder = fetchEntity(sequentialIdsOfInsertedTargetStockOrders.get(i), StockOrder.class);
                transaction.setTargetStockOrder(targetStockOrder);
                transaction.setOutputMeasureUnitType(targetStockOrder.getMeasurementUnitType());
                transaction.setTargetFacility(targetStockOrder.getFacility());

            } else {
                transaction.setOutputMeasureUnitType(transaction.getInputMeasureUnitType());
                transaction.setTargetFacility(transaction.getSourceFacility()); // TODO: As in nodeJS code, this should be fetched from ProcessingOrder which does not have a Facility?
            }

            if (!transaction.getIsProcessing() && (transaction.getSemiProduct() == null || !transaction.getSemiProduct().equals(transaction.getSourceStockOrder().getSemiProduct())))
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Target SemiProduct has to match source SemiProduct.");

            entity.getInputTransactions().add(transaction);
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Kaskada, ni druge");

        if (entity.getId() == null)
            em.persist(entity);

        System.out.println("Tukile pa nebi smel pridet...");
        return new ApiBaseEntity(entity);
    }

    @Transactional
    public void deleteProcessingOrder(Long id) throws ApiException {
        em.remove(fetchEntity(id, ProcessingOrder.class));
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
