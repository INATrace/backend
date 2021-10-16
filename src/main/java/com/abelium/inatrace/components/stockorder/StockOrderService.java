package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.payment.PaymentService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderActivityProof;
import com.abelium.inatrace.db.entities.stockorder.StockOrderLocation;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Lazy
@Service
public class StockOrderService extends BaseService {

    @Autowired
    private FacilityService facilityService;

    public ApiStockOrder getStockOrder(long id, Long userId) throws ApiException {
        return StockOrderMapper.toApiStockOrder(fetchEntity(id, StockOrder.class), userId);
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderList(ApiPaginatedRequest request,
                                                             StockOrderQueryRequest queryRequest,
                                                             Long userId) {
        return PaginationTools.createPaginatedResponse(em, request,
                () -> stockOrderQueryObject(
                        request,
                        queryRequest
                ), stockOrder -> StockOrderMapper.toApiStockOrder(stockOrder, userId));
    }

    private StockOrder stockOrderQueryObject(ApiPaginatedRequest request,
                                             StockOrderQueryRequest queryRequest) {

        StockOrder stockOrderProxy = Torpedo.from(StockOrder.class);
        OnGoingLogicalCondition condition = Torpedo.condition();

        // Only present when listing by facility or company
        if(queryRequest.companyId != null)
            condition = condition.and(stockOrderProxy.getCompany().getId()).eq(queryRequest.companyId);
        else if (queryRequest.facilityId != null)
            condition = condition.and(stockOrderProxy.getFacility().getId()).eq(queryRequest.facilityId);


        // Query parameter filters
        if(queryRequest.farmerId != null)
            condition.and(stockOrderProxy.getProducerUserCustomer()).isNotNull()
                    .and(stockOrderProxy.getProducerUserCustomer().getId()).eq(queryRequest.farmerId);
        if(queryRequest.semiProductId != null)
            condition.and(stockOrderProxy.getSemiProduct()).isNotNull()
                    .and(stockOrderProxy.getSemiProduct().getId()).eq(queryRequest.semiProductId);

        if(queryRequest.isOpenBalanceOnly != null && queryRequest.isOpenBalanceOnly)
            condition.and(stockOrderProxy.getBalance()).isNotNull()
                    .and(stockOrderProxy.getBalance()).gt(BigDecimal.ZERO);

        if (queryRequest.isPurchaseOrderOnly != null && queryRequest.isPurchaseOrderOnly) {
            condition.and(stockOrderProxy.getOrderType()).eq(OrderType.PURCHASE_ORDER);
        }

        if(queryRequest.isWomenShare != null)
            condition.and(stockOrderProxy.getWomenShare()).eq(queryRequest.isWomenShare);

        if(queryRequest.wayOfPayment != null)
            condition.and(stockOrderProxy.getPreferredWayOfPayment()).eq(queryRequest.wayOfPayment);

        if(queryRequest.orderType != null)
            condition.and(stockOrderProxy.getOrderType()).eq(queryRequest.orderType);

        if(queryRequest.productionDateStart != null)
            condition.and(stockOrderProxy.getProductionDate()).gte(queryRequest.productionDateStart);

        if(queryRequest.productionDateEnd != null)
            condition.and(stockOrderProxy.getProductionDate()).lte(queryRequest.productionDateEnd);

        if(queryRequest.producerUserCustomerName != null) // Search by farmers name (query)
            condition.and(stockOrderProxy.getProducerUserCustomer().getName())
                    .like().startsWith(queryRequest.producerUserCustomerName);

        if(queryRequest.isAvailable != null && queryRequest.isAvailable)
            condition.and(stockOrderProxy.getAvailableQuantity()).gt(0);

        Torpedo.where(condition);

        // Order by
        QueryTools.orderBy(request.sort, stockOrderProxy.getId());

        return stockOrderProxy;
    }

    @Transactional
    public ApiBaseEntity createOrUpdateStockOrder(ApiStockOrder apiStockOrder, Long userId, ProcessingOrder processingOrder) throws ApiException {

        StockOrder entity;

        if (apiStockOrder.getId() != null) {
            entity = fetchEntity(apiStockOrder.getId(), StockOrder.class);
            entity.setUpdatedBy(fetchEntity(userId, User.class));

        } else {
            entity = new StockOrder();
            entity.setCreatedBy(fetchEntity(userId, User.class));
            entity.setCreatorId(apiStockOrder.getCreatorId());
        }

        // Validation of required fields
        if(apiStockOrder.getOrderType() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "OrderType needs to be provided!");
        if(apiStockOrder.getFacility() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Facility needs to be provided!");
        if(apiStockOrder.getSemiProduct() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "SemiProduct needs to be provided!");

        entity.setOrderType(apiStockOrder.getOrderType());
        entity.setFacility(facilityService.fetchFacility(apiStockOrder.getFacility().getId()));
        entity.setCompany(entity.getFacility().getCompany());
        entity.setIdentifier(apiStockOrder.getIdentifier());
        entity.setPreferredWayOfPayment(apiStockOrder.getPreferredWayOfPayment());
        entity.setSacNumber(apiStockOrder.getSacNumber());
        entity.setProductionDate(apiStockOrder.getProductionDate());
        entity.setInternalLotNumber(apiStockOrder.getInternalLotNumber());
        entity.setDeliveryTime(apiStockOrder.getDeliveryTime());
        entity.setSemiProduct(fetchEntity(apiStockOrder.getSemiProduct().getId(), SemiProduct.class));
        entity.setOrganic(apiStockOrder.getOrganic());
        entity.setTare(apiStockOrder.getTare());
        entity.setWomenShare(apiStockOrder.getWomenShare());

        // Calculate quantities

        Integer lastTotalQuantity = entity.getTotalQuantity();
        entity.setTotalQuantity(apiStockOrder.getTotalQuantity());
        if (entity.getTotalQuantity() != null && lastTotalQuantity != null && entity.getAvailableQuantity() != null){
            entity.setAvailableQuantity(apiStockOrder.getTotalQuantity() - (lastTotalQuantity - entity.getAvailableQuantity()));
        } else {
            entity.setAvailableQuantity(entity.getTotalQuantity());
        }
        entity.setFulfilledQuantity(entity.getFulfilledQuantity());
        entity.setDamagedPriceDeduction(apiStockOrder.getDamagedPriceDeduction());
        entity.setCurrency(apiStockOrder.getCurrency());

        if (processingOrder != null && entity.getId() != null) {
            entity.setProcessingOrder(processingOrder);

            // Calculate quantities based on input transactions
            List<Transaction> inputTxs = processingOrder.getInputTransactions();
            if (inputTxs != null && !inputTxs.isEmpty()) {

                if (apiStockOrder.getOrderType() == OrderType.SALES_ORDER || apiStockOrder.getOrderType() == OrderType.GENERAL_ORDER)
                    entity.setFulfilledQuantity(calculateFulfilledQuantity(inputTxs, entity.getId()));
                else
                    entity.setFulfilledQuantity(entity.getTotalQuantity());

                if (apiStockOrder.getOrderType() != OrderType.SALES_ORDER)
                    entity.setAvailableQuantity(entity.getFulfilledQuantity() - calculateUsedQuantity(inputTxs, entity.getId()));
            }
        }

        // Validate quantities
        if (entity.getAvailableQuantity() != null) {

            if (entity.getAvailableQuantity() < 0)
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity resulted in negative number.");

            if (entity.getFulfilledQuantity() != null) {
                if (entity.getFulfilledQuantity() < entity.getAvailableQuantity())
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity (" + entity.getAvailableQuantity()
                            + ") cannot be bigger then fulfilled quantity (" + entity.getFulfilledQuantity() + ").");
            }
        }

        entity.setAvailable(entity.getAvailableQuantity() != null && entity.getAvailableQuantity() > 0);
        entity.setOpenOrder(entity.getOrderType() == OrderType.GENERAL_ORDER && entity.getTotalQuantity() > entity.getFulfilledQuantity());

        // END: Calculate quantities

        if(entity.getSemiProduct() != null)
            entity.setMeasurementUnitType(entity.getSemiProduct().getMeasurementUnitType());

        // Production location
        ApiStockOrderLocation apiProdLocation = apiStockOrder.getProductionLocation();
        if(apiProdLocation != null) {

            StockOrderLocation stockOrderLocation = fetchEntityOrElse(apiProdLocation.getId(), StockOrderLocation.class, null);

            if(stockOrderLocation == null)
                stockOrderLocation = new StockOrderLocation();

            stockOrderLocation.setLatitude(apiProdLocation.getLatitude());
            stockOrderLocation.setLongitude(apiProdLocation.getLongitude());
            stockOrderLocation.setNumberOfFarmers(apiProdLocation.getNumberOfFarmers());
            stockOrderLocation.setPinName(apiProdLocation.getPinName());
            entity.setProductionLocation(stockOrderLocation);
        }

        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:

                // Required
                if(apiStockOrder.getProducerUserCustomer() == null)
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Producer user customer is required for purchase orders!");

                // For new StockOrder set fresh values (after that only calculations are possible)
//                if (entity.getId() == null) {
//                    if (apiStockOrder.getFulfilledQuantity() == null)
//                        throw new ApiException(ApiStatus.VALIDATION_ERROR, "Fulfilled quantity needs to be provided!");
                    if (apiStockOrder.getTotalQuantity() == null)
                        throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total quantity needs to be provided!");
//                    if (apiStockOrder.getAvailableQuantity() == null)
//                        throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity needs to be provided!");
                    if (apiStockOrder.getPricePerUnit() == null)
                        throw new ApiException(ApiStatus.VALIDATION_ERROR, "Price per unit needs to be provided!");
//                }
                entity.setPricePerUnit(apiStockOrder.getPricePerUnit());
                entity.setCost(entity.getPricePerUnit().multiply(BigDecimal.valueOf(entity.getTotalQuantity())));
                if (processingOrder == null) {
                    entity.setBalance(calculateBalanceForPurchaseOrder(entity));
                } else if (entity.getId() == null){
                    entity.setBalance(entity.getCost());
                }
                entity.setPaid(entity.getCost().subtract(entity.getBalance()));

                entity.setProducerUserCustomer(fetchEntity(apiStockOrder.getProducerUserCustomer().getId(), UserCustomer.class));
                entity.setPurchaseOrder(true);

                // Optional
                if(apiStockOrder.getRepresentativeOfProducerUserCustomer() != null)
                    entity.setRepresentativeOfProducerUserCustomer(fetchEntity(apiStockOrder.getRepresentativeOfProducerUserCustomer().getId(), UserCustomer.class));

                // Create or update activity proofs
                entity.getActivityProofs().clear();

                for (ApiActivityProof apiAP : apiStockOrder.getActivityProofs()) {

                    Document activityProofDoc = fetchEntity(apiAP.getDocument().getId(), Document.class);

                    StockOrderActivityProof stockOrderActivityProof = new StockOrderActivityProof();
                    stockOrderActivityProof.setStockOrder(entity);
                    stockOrderActivityProof.setActivityProof(new ActivityProof());
                    stockOrderActivityProof.getActivityProof().setDocument(activityProofDoc);
                    stockOrderActivityProof.getActivityProof().setFormalCreationDate(apiAP.getFormalCreationDate());
                    stockOrderActivityProof.getActivityProof().setType(apiAP.getType());
                    stockOrderActivityProof.getActivityProof().setValidUntil(apiAP.getValidUntil());

                    entity.getActivityProofs().add(stockOrderActivityProof);
                }

                break;
            case SALES_ORDER:
                break;
            case GENERAL_ORDER:
                break;
            case TRANSFER_ORDER:
                break;
            case PROCESSING_ORDER:
                break;
        }

        // Remove documents not present in API request
//                entity.getDocumentRequirements().removeIf(dr -> apiStockOrder.getDocumentRequirements()
//                                .stream().noneMatch(apiDr -> dr.getId().equals(apiDr.getId())));

        // Add or update other documents
//                apiStockOrder.getDocumentRequirements().forEach(apiDr -> {
//
//                    StockOrderPETypeValue dr = fetchEntityOrElse(apiDr.getId(), StockOrderPETypeValue.class, new StockOrderPETypeValue());
//                    entity.getDocumentRequirements().remove(dr);
//                    dr.setName(apiDr.getName());
//                    dr.setDescription(apiDr.getDescription());
//                    // doc.setScoreTarget();
//                    // doc.setFields();
//                    // doc.setScoreTarget();
//                    entity.getDocumentRequirements().add(dr);
//                });

        if (entity.getId() == null) {
            em.persist(entity);
        }

        return new ApiBaseEntity(entity);
    }

    @Transactional
    public void deleteStockOrder(Long id) throws ApiException{
        StockOrder stockOrder = fetchEntity(id, StockOrder.class);
        em.remove(stockOrder);
    }

    public <E> E fetchEntity(Long id, Class<E> entityClass) throws ApiException {

        E entity = Queries.get(em, entityClass, id);
        if (entity == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid " + entityClass.getSimpleName() + " ID");
        }
        return entity;
    }

    private <E> E fetchEntityOrElse(Long id, Class<E> entityClass, E defaultValue) {
        E entity = Queries.get(em, entityClass, id);
        return entity == null ? defaultValue : entity;
    }

    private BigDecimal calculateBalanceForPurchaseOrder(StockOrder stockOrder) {
        if (stockOrder.getOrderType() != OrderType.PURCHASE_ORDER || stockOrder.getCost() == null)
            return null;


        List<Payment> paymentList = em.createNamedQuery("Payment.listPaymentsByPurchaseId", Payment.class)
                .setParameter("purchaseId", stockOrder.getId())
                .getResultList();

        return stockOrder.getCost()
                .subtract(paymentList.stream()
                        .map(payment -> payment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT
                                ? payment.getAmountPaidToTheFarmer().add(
                                        payment.getPreferredWayOfPayment() != PreferredWayOfPayment.CASH_VIA_COLLECTOR
                                                ? payment.getAmountPaidToTheCollector()
                                                : BigDecimal.ZERO)
                                : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private Integer calculateFulfilledQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (stockOrderId == null) return null;
        return inputTransactions.stream()
                .filter(t -> t.getSourceStockOrder() != null && stockOrderId.equals(t.getSourceStockOrder().getId()))
                .map(Transaction::getInputQuantity)
                .reduce(0, Integer::sum);
    }

    private Integer calculateUsedQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (stockOrderId == null) return null;
        return inputTransactions.stream()
                .filter(t -> t.getSourceStockOrder() != null && stockOrderId.equals(t.getSourceStockOrder().getId()))
                .map(Transaction::getOutputQuantity)
                .reduce(0, Integer::sum);
    }

}
