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
import com.abelium.inatrace.components.processingorder.mappers.ProcessingOrderMapper;
import com.abelium.inatrace.components.stockorder.api.*;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
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
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.types.Language;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public ApiStockOrder getStockOrder(long id, Long userId, Language language, Boolean withProcessingOrder) throws ApiException {
        return StockOrderMapper.toApiStockOrder(fetchEntity(id, StockOrder.class), userId, language, withProcessingOrder);
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderList(ApiPaginatedRequest request,
                                                             StockOrderQueryRequest queryRequest,
                                                             Long userId,
                                                             Language language) {
        return PaginationTools.createPaginatedResponse(em, request,
                () -> stockOrderQueryObject(
                        request,
                        queryRequest
                ), stockOrder -> StockOrderMapper.toApiStockOrder(stockOrder, userId, language));
    }

    private StockOrder stockOrderQueryObject(ApiPaginatedRequest request,
                                             StockOrderQueryRequest queryRequest) {

        StockOrder stockOrderProxy = Torpedo.from(StockOrder.class);
        OnGoingLogicalCondition condition = Torpedo.condition();

        if (queryRequest.companyId != null) {
            condition = condition.and(stockOrderProxy.getCompany().getId()).eq(queryRequest.companyId);
        }

        if (queryRequest.facilityId != null) {
            condition = condition.and(stockOrderProxy.getFacility().getId()).eq(queryRequest.facilityId);
        }

        // User for fetching quote orders (filter by Quote company)
        if (queryRequest.quoteCompanyId != null) {
            condition = condition.and(stockOrderProxy.getQuoteCompany().getId()).eq(queryRequest.quoteCompanyId);
        }

        // Used for fetching quote orders
        if (queryRequest.quoteFacilityId != null) {
            condition = condition.and(stockOrderProxy.getQuoteFacility().getId()).eq(queryRequest.quoteFacilityId);
        }

        // Used for fetching quote orders (filter by Quote facility)
        if (queryRequest.companyCustomerId != null) {
            condition = condition.and(stockOrderProxy.getConsumerCompanyCustomer()).isNotNull();
            condition = condition.and(stockOrderProxy.getConsumerCompanyCustomer().getId()).eq(queryRequest.companyCustomerId);
        }

        // Query parameter filters
        if(queryRequest.farmerId != null) {
            condition = condition.and(stockOrderProxy.getProducerUserCustomer()).isNotNull();
            condition = condition.and(stockOrderProxy.getProducerUserCustomer().getId()).eq(queryRequest.farmerId);
        }

        if(queryRequest.semiProductId != null) {
            condition = condition.and(stockOrderProxy.getSemiProduct()).isNotNull();
            condition = condition.and(stockOrderProxy.getSemiProduct().getId()).eq(queryRequest.semiProductId);
        }

        if(queryRequest.isOpenBalanceOnly != null && queryRequest.isOpenBalanceOnly) {
            condition = condition.and(stockOrderProxy.getBalance()).isNotNull();
            condition = condition.and(stockOrderProxy.getBalance()).gt(BigDecimal.ZERO);
        }

        if (queryRequest.isPurchaseOrderOnly != null && queryRequest.isPurchaseOrderOnly) {
            condition = condition.and(stockOrderProxy.getOrderType()).eq(OrderType.PURCHASE_ORDER);
        }

        if (queryRequest.isWomenShare != null) {
            condition = condition.and(stockOrderProxy.getWomenShare()).eq(queryRequest.isWomenShare);
        }

        if (queryRequest.wayOfPayment != null) {
            condition = condition.and(stockOrderProxy.getPreferredWayOfPayment()).eq(queryRequest.wayOfPayment);
        }

        if (queryRequest.orderType != null) {
            condition = condition.and(stockOrderProxy.getOrderType()).eq(queryRequest.orderType);
        }

        if (queryRequest.productionDateStart != null) {
            condition = condition.and(stockOrderProxy.getProductionDate()).gte(queryRequest.productionDateStart);
        }

        if (queryRequest.productionDateEnd != null) {
            condition = condition.and(stockOrderProxy.getProductionDate()).lte(queryRequest.productionDateEnd);
        }

        // Search by farmers name (query)
        if (queryRequest.producerUserCustomerName != null) {
            condition = condition.and(stockOrderProxy.getProducerUserCustomer().getName()).like()
                    .startsWith(queryRequest.producerUserCustomerName);
        }

        // Get only stock orders that have available quantity
        if (BooleanUtils.isTrue(queryRequest.isAvailable)) {
            condition = condition.and(stockOrderProxy.getAvailableQuantity()).gt(BigDecimal.ZERO);
        }

        // Used for fetching open quote orders
        if (BooleanUtils.isTrue(queryRequest.isOpenOnly)) {
            condition = condition.and(stockOrderProxy.getIsOpenOrder()).eq(true);
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

    public ApiPaginatedList<ApiStockOrderAggregatedHistory> getStockOrderAggregatedHistoryList(
            ApiPaginatedRequest request, Long id, Long userId, Language language) throws ApiException {

        StockOrder stockOrder = fetchEntity(id, StockOrder.class);

        // recursively add history, starting from depth 0
        List<ApiStockOrderAggregatedHistory> stockAggregationHistoryList = addNextAggregationLevels(0, request,
                stockOrder, userId, language);

        stockAggregationHistoryList.sort(Comparator.comparingInt(ApiStockOrderAggregatedHistory::getDepth));

        ApiPaginatedList<ApiStockOrderAggregatedHistory> apiPaginatedList = new ApiPaginatedList<>();
        apiPaginatedList.setItems(stockAggregationHistoryList);

        if (!stockAggregationHistoryList.isEmpty()) {

            List<Transaction> outputTransactions = findOutputTransactions(stockOrder);

            // set output transactions only on first (root) element
            if (stockAggregationHistoryList.get(0).getProcessingOrder() == null) {

                if (outputTransactions != null && !outputTransactions.isEmpty()) {

                    stockAggregationHistoryList.get(0).setProcessingOrder(ProcessingOrderMapper
                            .toApiProcessingOrder(outputTransactions.get(0).getTargetProcessingOrder(), language));
                    stockAggregationHistoryList.get(0).getProcessingOrder().setOutputTransactions(
                            outputTransactions.stream()
                                    .map(transaction -> TransactionMapper.toApiTransaction(transaction, language))
                                    .collect(Collectors.toList()));
                }
            }
        }

        // paginated info is based on depth
        apiPaginatedList.setLimit(request.getLimit());
        apiPaginatedList.setOffset(request.getOffset());

        if (!stockAggregationHistoryList.isEmpty()) {
            // set count - depth of th last item
            apiPaginatedList.setCount(stockAggregationHistoryList.get(stockAggregationHistoryList.size() - 1).getDepth());

        } else {
            apiPaginatedList.setCount(0);
        }

        return apiPaginatedList;
    }

    private List<Transaction> findOutputTransactions(StockOrder stockOrder) {
        // read involved transactions, and set them as output transactions
        TypedQuery<Transaction> getTransactionsBySourceStockOrderQuery = em
                .createNamedQuery("Transaction.getOutputTransactionsByStockOrderId", Transaction.class)
                .setParameter("stockOrderId", stockOrder.getId());

        return getTransactionsBySourceStockOrderQuery.getResultList();
    }

    /***
     * Recursively adds next stock aggregation history list.
     *
     * @param currentDepth - aggregated history depth level
     * @param paginatedRequest - pagination, only levels specified are returned
     * @param stockOrder - current stockOrder entity for searching next child nodes
     * @param userId - caller user id
     * @param language - language
     *
     * @return returns aggregated history list for next levels
     *
     */
    private List<ApiStockOrderAggregatedHistory> addNextAggregationLevels(int currentDepth,
                                                                          ApiPaginatedRequest paginatedRequest,
                                                                          StockOrder stockOrder, Long userId, Language language) {

        if (stockOrder != null) {

            List<ApiStockOrderAggregatedHistory> resultHistoryList = new ArrayList<>();

            ApiStockOrderAggregatedHistory nextHistory = new ApiStockOrderAggregatedHistory();

            List<ApiStockOrderAggregation> nextAggregations = new ArrayList<>();

            if (stockOrder.getProcessingOrder() != null && !stockOrder.getProcessingOrder().getInputTransactions().isEmpty()) {

               // get stockOrders that are inputs connected to this stockOrder, via the processing order
               List<StockOrder> sourceOrderList = stockOrder.getProcessingOrder().getInputTransactions().stream().map(Transaction::getSourceStockOrder).collect(Collectors.toList());

                // get siblings list for this order
                List<ApiStockOrder> siblingsStockOrderList = stockOrder.getProcessingOrder().getTargetStockOrders().stream()
                        .map(siblingOrder -> StockOrderMapper.toApiStockOrder(siblingOrder, userId, language)).collect(
                        Collectors.toList());

                siblingsStockOrderList.forEach(siblingOrder -> {
                    ApiStockOrderAggregation aggregation = new ApiStockOrderAggregation();
                    aggregation.setStockOrder(siblingOrder);
                    //    aggregation.setFields(new ArrayList<>());// TODO: map fields
                    //    aggregation.setDocuments(new ArrayList<>()); // todo map documents
                    nextAggregations.add(aggregation);
                });

                nextHistory.setStockOrder(StockOrderMapper.toApiStockOrder(stockOrder, userId, language));

                nextHistory.setAggregations(nextAggregations);
                nextHistory.setProcessingOrder(
                        ProcessingOrderMapper.toApiProcessingOrder(stockOrder.getProcessingOrder(), language));
                nextHistory.setDepth(currentDepth);

                if (currentDepth >= paginatedRequest.getOffset() + paginatedRequest.getLimit() - 1) {
                    // break if upper limit
                    return new ArrayList<>();
                } else {
                    // next recursion for every child element
                    if (sourceOrderList.get(0).getSacNumber() != null){
                        // if sac number present, proceed with only first item leafs
                        resultHistoryList
                                .addAll(addNextAggregationLevels(currentDepth + 1, paginatedRequest, sourceOrderList.get(0), userId, language));
                    } else {
                        // proceed recursion with all leafs
                          sourceOrderList.forEach(sourceOrder -> {
                              resultHistoryList
                                      .addAll(addNextAggregationLevels(currentDepth + 1, paginatedRequest, sourceOrder, userId, language));

                          });
                    }

                }
            } else {
                // map last element, that does not contain processing order
                ApiStockOrderAggregation aggregation = new ApiStockOrderAggregation();
                aggregation.setStockOrder(StockOrderMapper.toApiStockOrder(stockOrder, userId, language));
                //    aggregation.setFields(new ArrayList<>());// TODO: map fields
                //    aggregation.setDocuments(new ArrayList<>()); // todo map documents
                nextAggregations.add(aggregation);

                nextHistory.setStockOrder(StockOrderMapper.toApiStockOrder(stockOrder, userId, language));

                nextHistory.setAggregations(nextAggregations);
                nextHistory.setProcessingOrder(
                        ProcessingOrderMapper.toApiProcessingOrder(stockOrder.getProcessingOrder(), language));
                nextHistory.setDepth(currentDepth);
            }

            // add when paginated
            if ((currentDepth > paginatedRequest.getOffset() - 1) &&
                    (currentDepth < paginatedRequest.getOffset() + paginatedRequest.getLimit() - 1)) {
                resultHistoryList.add(nextHistory);
            }

            return resultHistoryList;

        } else {
            return new ArrayList<>();
        }
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
            ApiBaseEntity apiBaseEntity = createOrUpdateStockOrder(apiStockOrder, userId, null);

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
            apiStockOrder.setTotalQuantity(farmer.getTotalQuantity());
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
        BigDecimal lastUsedQuantity = (entity.getFulfilledQuantity() != null && entity.getAvailableQuantity() != null)
                ? entity.getFulfilledQuantity().subtract(entity.getAvailableQuantity())
                : null;

        entity.setTotalQuantity(apiStockOrder.getTotalQuantity());

        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:
            case PROCESSING_ORDER:
            case TRANSFER_ORDER:

                entity.setFulfilledQuantity(entity.getTotalQuantity());

                // For new StockOrders set available quantity to total quantity
                if (entity.getId() == null) {
                    entity.setAvailableQuantity(entity.getFulfilledQuantity());
                }

                break;
            default:

                // Applies only for new Quote StockOrders
                if (entity.getId() == null) {
                    entity.setFulfilledQuantity(apiStockOrder.getFulfilledQuantity());
                    entity.setAvailableQuantity(apiStockOrder.getAvailableQuantity());
                }
        }

        if (processingOrder != null && entity.getId() != null) {

            // Calculate quantities based on input transactions
            List<Transaction> inputTxs = processingOrder.getInputTransactions();

            if (apiStockOrder.getOrderType() == OrderType.GENERAL_ORDER) {
                entity.setFulfilledQuantity(calculateFulfilledQuantity(inputTxs));
                // TODO: Used quantity needs to be reduced!
                entity.setAvailableQuantity(calculateAvailableQuantity(inputTxs));
            } else {
                if (lastUsedQuantity == null) {
                    lastUsedQuantity = BigDecimal.ZERO;
                }
                entity.setAvailableQuantity(entity.getFulfilledQuantity().subtract(lastUsedQuantity).subtract(calculateUsedQuantity(inputTxs, entity.getId())));
            }

        } else if (entity.getTotalQuantity() != null && lastUsedQuantity != null && entity.getAvailableQuantity() != null){
            entity.setAvailableQuantity(apiStockOrder.getFulfilledQuantity().subtract(lastUsedQuantity));
        }

        // Validate quantities
        if (entity.getAvailableQuantity() != null) {

            if (entity.getAvailableQuantity().compareTo(BigDecimal.ZERO) < 0) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity resulted in negative number.");
            }

            if (entity.getFulfilledQuantity() != null) {
                if (entity.getFulfilledQuantity().compareTo(entity.getAvailableQuantity()) < 0) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity (" + entity.getAvailableQuantity()
                            + ") cannot be bigger then fulfilled quantity (" + entity.getFulfilledQuantity() + ").");
                }
                if (entity.getFulfilledQuantity().compareTo(entity.getTotalQuantity()) > 0) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total quantity (" + entity.getTotalQuantity()
                            + ") cannot be bigger then fulfilled quantity (" + entity.getFulfilledQuantity() + ").");
                }
            }
        }

        entity.setAvailable(entity.getAvailableQuantity() != null && entity.getAvailableQuantity().compareTo(BigDecimal.ZERO) > 0);
        entity.setIsOpenOrder(entity.getOrderType() == OrderType.GENERAL_ORDER && entity.getTotalQuantity().compareTo(entity.getFulfilledQuantity()) > 0);

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

                // on purchase order, Total quantity is calculated by total gross quantity and tare
                if (apiStockOrder.getTare() != null) {
                    apiStockOrder.setTotalQuantity(apiStockOrder.getTotalGrossQuantity().subtract(apiStockOrder.getTare()));
                } else {
                    apiStockOrder.setTotalQuantity(apiStockOrder.getTotalGrossQuantity());
                }
                entity.setTotalQuantity(apiStockOrder.getTotalQuantity());
                entity.setTotalGrossQuantity(apiStockOrder.getTotalGrossQuantity());

                // Required
                if (apiStockOrder.getProducerUserCustomer() == null) {
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Producer user customer is required for purchase orders!");
                }

                if (apiStockOrder.getTotalQuantity() == null) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total quantity needs to be provided!");
                }

                if (apiStockOrder.getPricePerUnit() == null) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Price per unit needs to be provided!");
                }

                entity.setPricePerUnit(apiStockOrder.getPricePerUnit());
                BigDecimal pricePerUnitReduced = entity.getPricePerUnit();
                if (entity.getDamagedPriceDeduction() != null){
                    pricePerUnitReduced = entity.getPricePerUnit().subtract(entity.getDamagedPriceDeduction());
                }
                entity.setCost(pricePerUnitReduced.multiply(entity.getTotalQuantity()));
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
            case GENERAL_ORDER:

                // Set the quote facility and quote company
                entity.setQuoteFacility(facilityService.fetchFacility(apiStockOrder.getQuoteFacility().getId()));
                entity.setQuoteCompany(entity.getQuoteFacility().getCompany());

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
    }

    private BigDecimal calculateFulfilledQuantity(List<Transaction> inputTransactions){
        if (inputTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return inputTransactions.stream()
                .filter(t -> !t.getStatus().equals(TransactionStatus.CANCELED))
                .map(Transaction::getInputQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAvailableQuantity(List<Transaction> inputTransactions){
        if (inputTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return inputTransactions.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.EXECUTED))
                .map(Transaction::getInputQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateUsedQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (stockOrderId == null || inputTransactions.isEmpty())
            return BigDecimal.ZERO;

        return inputTransactions.stream()
                .filter(t -> t.getSourceStockOrder() != null
                        && stockOrderId.equals(t.getSourceStockOrder().getId())
                        && !t.getStatus().equals(TransactionStatus.CANCELED))
                .map(Transaction::getOutputQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
