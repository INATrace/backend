package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processing_evidence_type.ProcessingEvidenceTypeService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.processingevidencefield.ProcessingEvidenceFieldService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderEvidenceFieldValue;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderEvidenceTypeValue;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.components.stockorder.api.*;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.*;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.apache.commons.lang3.BooleanUtils;
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

    private final FacilityService facilityService;

    private final ProcessingEvidenceFieldService procEvidenceFieldService;

    private final ProcessingEvidenceTypeService procEvidenceTypeService;

    @Autowired
    public StockOrderService(FacilityService facilityService,
                             ProcessingEvidenceFieldService procEvidenceFieldService,
                             ProcessingEvidenceTypeService procEvidenceTypeService) {
        this.facilityService = facilityService;
        this.procEvidenceFieldService = procEvidenceFieldService;
        this.procEvidenceTypeService = procEvidenceTypeService;
    }

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
        if (queryRequest.companyId != null) {
            condition = condition.and(stockOrderProxy.getCompany().getId()).eq(queryRequest.companyId);
        } else if (queryRequest.facilityId != null) {
            condition = condition.and(stockOrderProxy.getFacility().getId()).eq(queryRequest.facilityId);
        }

        // Used for fetching quote orders
        if (queryRequest.quoteFacilityId != null) {
            condition = condition.and(stockOrderProxy.getQuoteFacility().getId()).eq(queryRequest.quoteFacilityId);
        }

        // Used for fetching quote orders
        if (queryRequest.companyCustomerId != null) {
            condition = condition
                    .and(stockOrderProxy.getConsumerCompanyCustomer()).isNotNull()
                    .and(stockOrderProxy.getConsumerCompanyCustomer().getId()).eq(queryRequest.companyCustomerId);
        }

        // Query parameter filters
        if(queryRequest.farmerId != null) {
            condition
                    .and(stockOrderProxy.getProducerUserCustomer()).isNotNull()
                    .and(stockOrderProxy.getProducerUserCustomer().getId()).eq(queryRequest.farmerId);
        }

        if(queryRequest.semiProductId != null) {
            condition
                    .and(stockOrderProxy.getSemiProduct()).isNotNull()
                    .and(stockOrderProxy.getSemiProduct().getId()).eq(queryRequest.semiProductId);
        }

        if(queryRequest.isOpenBalanceOnly != null && queryRequest.isOpenBalanceOnly) {
            condition
                    .and(stockOrderProxy.getBalance()).isNotNull()
                    .and(stockOrderProxy.getBalance()).gt(BigDecimal.ZERO);
        }

        if (queryRequest.isPurchaseOrderOnly != null && queryRequest.isPurchaseOrderOnly) {
            condition.and(stockOrderProxy.getOrderType()).eq(OrderType.PURCHASE_ORDER);
        }

        if (queryRequest.isWomenShare != null) {
            condition.and(stockOrderProxy.getWomenShare()).eq(queryRequest.isWomenShare);
        }

        if (queryRequest.wayOfPayment != null) {
            condition.and(stockOrderProxy.getPreferredWayOfPayment()).eq(queryRequest.wayOfPayment);
        }

        if (queryRequest.orderType != null) {
            condition.and(stockOrderProxy.getOrderType()).eq(queryRequest.orderType);
        }

        if (queryRequest.productionDateStart != null) {
            condition.and(stockOrderProxy.getProductionDate()).gte(queryRequest.productionDateStart);
        }

        if (queryRequest.productionDateEnd != null) {
            condition.and(stockOrderProxy.getProductionDate()).lte(queryRequest.productionDateEnd);
        }

        // Search by farmers name (query)
        if (queryRequest.producerUserCustomerName != null) {
            condition.and(stockOrderProxy.getProducerUserCustomer().getName()).like()
                    .startsWith(queryRequest.producerUserCustomerName);
        }

        // Get only stock orders that have available quantity
        if (BooleanUtils.isTrue(queryRequest.isAvailable)) {
            condition.and(stockOrderProxy.getAvailableQuantity()).gt(BigDecimal.ZERO);
        }

        // Used for fetching open quote orders
        if (BooleanUtils.isTrue(queryRequest.isOpenOnly)) {
            condition.and(stockOrderProxy.getTotalQuantity()).gt(stockOrderProxy.getFulfilledQuantity());
        }

        Torpedo.where(condition);

        // Order by
        switch (request.sortBy) {
            case "date":
                QueryTools.orderBy(request.sort, stockOrderProxy.getProductionDate());
                break;
            case "deliveryTime":
                QueryTools.orderBy(request.sort, stockOrderProxy.getDeliveryTime());
                break;
            case "updateTimestamp":
                QueryTools.orderBy(request.sort, stockOrderProxy.getUpdateTimestamp());
                break;
            default:
                QueryTools.orderBy(request.sort, stockOrderProxy.getId());
        }

        return stockOrderProxy;
    }

    @Transactional
    public ApiPurchaseOrder createPurchaseBulkOrder(ApiPurchaseOrder apiPurchaseOrder, Long userId) throws ApiException {

        // Validation
        if (apiPurchaseOrder == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "ApiPurchaseOrder needs to be provided!");
        }
        if (apiPurchaseOrder.getFarmers() == null || apiPurchaseOrder.getFarmers().isEmpty()){
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Farmers needs to be provided!");
        }

        // Update stocks of type Purchase, one by one
        for (ApiPurchaseOrderFarmer farmer : apiPurchaseOrder.getFarmers()) {

            // convert to ApiStockOrder of type PURCHASE_ORDER
            ApiStockOrder apiStockOrder = convertPurchaseOrderFarmerToStockOrder(apiPurchaseOrder, farmer);

            // write to db
            ApiBaseEntity apiBaseEntity = createOrUpdateStockOrder(apiStockOrder, userId);

            // set Id in response
            farmer.setId(apiBaseEntity.getId());
        }

        return apiPurchaseOrder;
    }

    private ApiStockOrder convertPurchaseOrderFarmerToStockOrder(ApiPurchaseOrder apiPurchaseOrder, ApiPurchaseOrderFarmer farmer){

        ApiStockOrder apiStockOrder = new ApiStockOrder();

        if (apiPurchaseOrder != null) {

            apiStockOrder.setOrderType(OrderType.PURCHASE_ORDER);

            // copy common attributes
            apiStockOrder.setCreatorId(apiPurchaseOrder.getCreatorId());
            apiStockOrder.setDeliveryTime(apiPurchaseOrder.getDeliveryTime());
            apiStockOrder.setFacility(apiPurchaseOrder.getFacility());
            apiStockOrder.setPreferredWayOfPayment(apiPurchaseOrder.getPreferredWayOfPayment());
            apiStockOrder.setRepresentativeOfProducerUserCustomer(apiPurchaseOrder.getRepresentativeOfProducerUserCustomer());
            apiStockOrder.setProductionDate(apiPurchaseOrder.getProductionDate());
            apiStockOrder.setCurrency(apiPurchaseOrder.getCurrency());
            apiStockOrder.setActivityProofs(apiPurchaseOrder.getActivityProofs());

            // copy farmer attributes
            apiStockOrder.setIdentifier(farmer.getIdentifier());
            apiStockOrder.setOrganic(farmer.getOrganic());
            apiStockOrder.setDamagedPriceDeduction(farmer.getDamagedPriceDeduction());
            apiStockOrder.setSemiProduct(farmer.getSemiProduct());
            apiStockOrder.setTare(farmer.getTare());
            apiStockOrder.setPricePerUnit(farmer.getPricePerUnit());
            apiStockOrder.setCost(farmer.getCost());
            apiStockOrder.setBalance(farmer.getBalance());
            apiStockOrder.setProducerUserCustomer(farmer.getProducerUserCustomer());
            apiStockOrder.setWomenShare(farmer.getWomenShare());
            apiStockOrder.setAvailableQuantity(farmer.getAvailableQuantity());
            apiStockOrder.setFulfilledQuantity(farmer.getFulfilledQuantity());
            apiStockOrder.setTotalGrossQuantity(farmer.getTotalGrossQuantity());

        }

        return apiStockOrder;
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
        if (apiStockOrder.getOrderType() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "OrderType needs to be provided!");
        if (apiStockOrder.getFacility() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Facility needs to be provided!");
        if (apiStockOrder.getSemiProduct() == null)
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
        entity.setDamagedPriceDeduction(apiStockOrder.getDamagedPriceDeduction());
        entity.setCurrency(apiStockOrder.getCurrency());

        // Calculate quantities

        // set total quantity
        if (apiStockOrder.getTare() != null) {
            entity.setTotalQuantity(apiStockOrder.getTotalGrossQuantity().subtract(apiStockOrder.getTare()));
        } else {
            entity.setTotalQuantity(apiStockOrder.getTotalGrossQuantity());
        }

        Integer lastUsedQuantity = (entity.getTotalQuantity() != null && entity.getAvailableQuantity() != null)
                ? entity.getTotalQuantity() - entity.getAvailableQuantity()
                : null;

        entity.setTotalQuantity(apiStockOrder.getTotalQuantity());

        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:
            case PROCESSING_ORDER:
            case TRANSFER_ORDER:
                entity.setFulfilledQuantity(entity.getTotalQuantity());

                // For new StockOrders set available quantity to total quantity
                if (entity.getId() == null) {
                    entity.setAvailableQuantity(entity.getTotalQuantity());
                }

            default:
                // Applies only for new Quote StockOrders
                if (entity.getId() == null) {
                    entity.setFulfilledQuantity(apiStockOrder.getFulfilledQuantity());
                    entity.setAvailableQuantity(apiStockOrder.getFulfilledQuantity());
                }
        }

        if (processingOrder != null && entity.getId() != null) {

            // Calculate quantities based on input transactions
            List<Transaction> inputTxs = processingOrder.getInputTransactions();
            if (inputTxs != null && !inputTxs.isEmpty()) {

                if (apiStockOrder.getOrderType() == OrderType.SALES_ORDER || apiStockOrder.getOrderType() == OrderType.GENERAL_ORDER) {
                    // TODO: May happen that old transactions are not counted
                    entity.setFulfilledQuantity(calculateFulfilledQuantity(inputTxs, entity.getId()));
                }

                if (apiStockOrder.getOrderType() != OrderType.SALES_ORDER) {
                    if(lastUsedQuantity == null) {
                        lastUsedQuantity = 0;
                    }
                    entity.setAvailableQuantity(entity.getFulfilledQuantity() - lastUsedQuantity - calculateUsedQuantity(inputTxs, entity.getId()));
                }
            }

        } else if (entity.getTotalQuantity() != null && lastUsedQuantity != null && entity.getAvailableQuantity() != null){
            entity.setAvailableQuantity(apiStockOrder.getTotalQuantity() - lastUsedQuantity);

        }

        // Validate quantities
        if (entity.getAvailableQuantity() != null) {

            if (entity.getAvailableQuantity() < 0) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity resulted in negative number.");
            }

            if (entity.getFulfilledQuantity() != null) {
                if (entity.getFulfilledQuantity() < entity.getAvailableQuantity()) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity (" + entity.getAvailableQuantity()
                            + ") cannot be bigger then fulfilled quantity (" + entity.getFulfilledQuantity() + ").");
                }
            }
        }

        entity.setAvailable(entity.getAvailableQuantity() != null && entity.getAvailableQuantity() > 0);
        entity.setOpenOrder(entity.getOrderType() == OrderType.GENERAL_ORDER && entity.getTotalQuantity() > entity.getFulfilledQuantity());

        // END: Calculate quantities

        if (entity.getSemiProduct() != null) {
            entity.setMeasurementUnitType(entity.getSemiProduct().getMeasurementUnitType());
        }

        // Production location
        ApiStockOrderLocation apiProdLocation = apiStockOrder.getProductionLocation();
        if (apiProdLocation != null) {

            StockOrderLocation stockOrderLocation = fetchEntityOrElse(apiProdLocation.getId(), StockOrderLocation.class, null);

            if(stockOrderLocation == null) {
                stockOrderLocation = new StockOrderLocation();
            }

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
                if (apiStockOrder.getTotalQuantity() == null)
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total quantity needs to be provided!");
                if (apiStockOrder.getPricePerUnit() == null)
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Price per unit needs to be provided!");

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
            case GENERAL_ORDER:
            case TRANSFER_ORDER:
            case PROCESSING_ORDER:

                // Create/update processing evidence fields instances (values)
                createOrUpdateEvidenceFieldValues(apiStockOrder.getRequiredEvidenceFieldValues(), entity);

                // Create/update processing evidence types instances (values) - both required and other evidences
                entity.getDocumentRequirements().clear();
                createOrUpdateEvidenceTypeValues(apiStockOrder.getRequiredEvidenceTypeValues(), entity, false);
                createOrUpdateEvidenceTypeValues(apiStockOrder.getOtherEvidenceDocuments(), entity, true);
        }

        if (entity.getId() == null) {
            em.persist(entity);
        }

        return new ApiBaseEntity(entity);
    }

    // Should be called only from ProcessingOrderService
    @Transactional
    public StockOrder createOrUpdateQuoteStockOrder(ApiStockOrder apiQuoteStockOrder, Long userId, ProcessingOrder processingOrder) throws ApiException {

        StockOrder entity;
        boolean inserted = false;

        if (apiQuoteStockOrder.getId() != null) {
            entity = fetchEntity(apiQuoteStockOrder.getId(), StockOrder.class);

        } else {
            if (apiQuoteStockOrder.getFulfilledQuantity() != null && apiQuoteStockOrder.getFulfilledQuantity() != 0) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Fulfilled quantity must be 0");
            }

            Long insertedStockOrderId = createOrUpdateStockOrder(apiQuoteStockOrder, userId, processingOrder).getId();
            entity = fetchEntity(insertedStockOrderId, StockOrder.class);
            inserted = true;
        }

        if (apiQuoteStockOrder.getOrderType() != OrderType.GENERAL_ORDER && apiQuoteStockOrder.getOrderType() != OrderType.SALES_ORDER) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Order must be of orderType " +  OrderType.GENERAL_ORDER
                    + " or " + OrderType.SALES_ORDER + " to allow input transactions");
        }

        if (!inserted) {

            // Do not mess with quantities - transactions will take care of it.
            apiQuoteStockOrder.setTotalQuantity(entity.getTotalQuantity());
            apiQuoteStockOrder.setAvailableQuantity(entity.getAvailableQuantity());
            apiQuoteStockOrder.setFulfilledQuantity(entity.getFulfilledQuantity());

            createOrUpdateStockOrder(apiQuoteStockOrder, userId, processingOrder);
            return fetchEntity(apiQuoteStockOrder.getId(), StockOrder.class);
        }

        return entity;
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

        BigDecimal balance = stockOrder.getCost();
        for (Payment payment : paymentList) {

            if (payment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT) {
                balance = balance.subtract(payment.getAmountPaidToTheFarmer());

                if (payment.getPreferredWayOfPayment() != PreferredWayOfPayment.CASH_VIA_COLLECTOR) {
                    balance = balance.subtract(payment.getAmountPaidToTheCollector());
                }
            }
        }
        return balance;

//        return stockOrder.getCost()
//                .subtract(paymentList.stream()
//                        .map(payment -> payment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT
//                                ? payment.getAmountPaidToTheFarmer().add(
//                                        payment.getPreferredWayOfPayment() != PreferredWayOfPayment.CASH_VIA_COLLECTOR
//                                                ? payment.getAmountPaidToTheCollector()
//                                                : BigDecimal.ZERO)
//                                : BigDecimal.ZERO)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private Integer calculateFulfilledQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (stockOrderId == null)
            return null;

        return inputTransactions.stream()
                .filter(t -> t.getSourceStockOrder() != null && stockOrderId.equals(t.getSourceStockOrder().getId()))
                .map(Transaction::getInputQuantity)
                .reduce(0, Integer::sum);
    }

    private Integer calculateUsedQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (stockOrderId == null)
            return null;

        return inputTransactions.stream()
                .filter(t -> t.getSourceStockOrder() != null && stockOrderId.equals(t.getSourceStockOrder().getId()))
                .map(Transaction::getOutputQuantity)
                .reduce(0, Integer::sum);
    }

    private void createOrUpdateEvidenceFieldValues(List<ApiStockOrderEvidenceFieldValue> apiEvidenceFieldValues, StockOrder entity) throws ApiException {

        entity.getProcessingEFValues().clear();

        for (ApiStockOrderEvidenceFieldValue apiEFV : apiEvidenceFieldValues) {

            StockOrderPEFieldValue stockOrderPEFieldValue = new StockOrderPEFieldValue();
            stockOrderPEFieldValue.setStockOrder(entity);
            stockOrderPEFieldValue.setProcessingEvidenceField(
                    procEvidenceFieldService.fetchProcessingEvidenceField(apiEFV.getEvidenceFieldId()));
            stockOrderPEFieldValue.setBooleanValue(apiEFV.getBooleanValue());
            stockOrderPEFieldValue.setStringValue(apiEFV.getStringValue());
            stockOrderPEFieldValue.setInstantValue(apiEFV.getDateValue());
            stockOrderPEFieldValue.setNumericValue(apiEFV.getNumericValue());
            stockOrderPEFieldValue.setStringValue(apiEFV.getStringValue());

            entity.getProcessingEFValues().add(stockOrderPEFieldValue);
        }
    }

    private void createOrUpdateEvidenceTypeValues(List<ApiStockOrderEvidenceTypeValue> apiEvidenceTypeValues, StockOrder entity, boolean otherEvidence) throws ApiException {

        for (ApiStockOrderEvidenceTypeValue apiETV : apiEvidenceTypeValues) {

            StockOrderPETypeValue stockOrderPETypeValue = new StockOrderPETypeValue();
            stockOrderPETypeValue.setStockOrder(entity);
            stockOrderPETypeValue.setProcessingEvidenceType(
                    procEvidenceTypeService.fetchProcessingEvidenceType(apiETV.getEvidenceTypeId()));
            stockOrderPETypeValue.setDate(apiETV.getDate());
            stockOrderPETypeValue.setDocument(fetchEntity(apiETV.getDocument().getId(), Document.class));
            stockOrderPETypeValue.setOtherEvidence(otherEvidence);

            entity.getDocumentRequirements().add(stockOrderPETypeValue);
        }
    }

}
