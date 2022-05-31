package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.processing_evidence_type.ProcessingEvidenceTypeService;
import com.abelium.inatrace.components.codebook.processingevidencefield.ProcessingEvidenceFieldService;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.processingorder.mappers.ProcessingOrderMapper;
import com.abelium.inatrace.components.product.FinalProductService;
import com.abelium.inatrace.components.stockorder.api.*;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.components.transaction.mappers.TransactionMapper;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.productorder.ProductOrder;
import com.abelium.inatrace.db.entities.stockorder.*;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Lazy
@Service
public class StockOrderService extends BaseService {

    private final FacilityService facilityService;

    private final ProcessingEvidenceFieldService procEvidenceFieldService;

    private final ProcessingEvidenceTypeService procEvidenceTypeService;

    private final SemiProductService semiProductService;

    private final FinalProductService finalProductService;

    @Autowired
    public StockOrderService(FacilityService facilityService,
                             ProcessingEvidenceFieldService procEvidenceFieldService,
                             ProcessingEvidenceTypeService procEvidenceTypeService,
                             SemiProductService semiProductService,
                             FinalProductService finalProductService) {
        this.facilityService = facilityService;
        this.procEvidenceFieldService = procEvidenceFieldService;
        this.procEvidenceTypeService = procEvidenceTypeService;
        this.semiProductService = semiProductService;
        this.finalProductService = finalProductService;
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
        if (queryRequest.farmerId != null) {
            condition = condition.and(stockOrderProxy.getProducerUserCustomer()).isNotNull();
            condition = condition.and(stockOrderProxy.getProducerUserCustomer().getId()).eq(queryRequest.farmerId);
        }

        // Filer by collector (used in purchase orders)
        if (queryRequest.representativeOfProducerUserCustomerId != null) {
            condition = condition.and(stockOrderProxy.getRepresentativeOfProducerUserCustomer()).isNotNull();
            condition = condition.and(stockOrderProxy.getRepresentativeOfProducerUserCustomer().getId())
                    .eq(queryRequest.representativeOfProducerUserCustomerId);
        }

        // Used for filtering stock orders by semi-product
        if (queryRequest.semiProductId != null) {
            condition = condition.and(stockOrderProxy.getSemiProduct()).isNotNull();
            condition = condition.and(stockOrderProxy.getSemiProduct().getId()).eq(queryRequest.semiProductId);
        }

        // Used for filtering stock orders by final product
        if (queryRequest.finalProductId != null) {
            condition = condition.and(stockOrderProxy.getFinalProduct()).isNotNull();
            condition = condition.and(stockOrderProxy.getFinalProduct().getId()).eq(queryRequest.finalProductId);
        }

        if (queryRequest.isOpenBalanceOnly != null && queryRequest.isOpenBalanceOnly) {
            condition = condition.and(stockOrderProxy.getBalance()).isNotNull();
            condition = condition.and(stockOrderProxy.getBalance()).gt(BigDecimal.ZERO);
        }

        if (queryRequest.isPurchaseOrderOnly != null && queryRequest.isPurchaseOrderOnly) {
            condition = condition.and(stockOrderProxy.getOrderType()).eq(OrderType.PURCHASE_ORDER);
        }

        if (queryRequest.isWomenShare != null) {
            condition = condition.and(stockOrderProxy.getWomenShare()).eq(queryRequest.isWomenShare);
        }

        if (queryRequest.organicOnly != null) {
            condition = condition.and(stockOrderProxy.getOrganic()).eq(queryRequest.organicOnly);
        }

        // If LOT name is provided filter by LOT prefix or LOT name
        if (StringUtils.isNotBlank(queryRequest.internalLotName)) {
            OnGoingLogicalCondition likeInternalLotName = Torpedo.condition(stockOrderProxy.getInternalLotNumber()).like().any(queryRequest.internalLotName);
            OnGoingLogicalCondition likeLotPrefix = Torpedo.condition(stockOrderProxy.getLotPrefix()).like().any(queryRequest.internalLotName);
            condition = condition.and(Torpedo.condition(likeInternalLotName.or(likeLotPrefix)));
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
            condition = condition.and(stockOrderProxy.getProducerUserCustomer()).isNotNull();
            OnGoingLogicalCondition likeName = Torpedo.condition(stockOrderProxy.getProducerUserCustomer().getName()).like().any(queryRequest.producerUserCustomerName);
            OnGoingLogicalCondition likeSurname = Torpedo.condition(stockOrderProxy.getProducerUserCustomer().getSurname()).like().any(queryRequest.producerUserCustomerName);
            condition = condition.and(Torpedo.condition(likeName.or(likeSurname)));
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

    /**
     * Return the public data for the stock order(s) with the provided QR code tag. Multiple stock orders can contain the provided QR code tag.
     * @param qrTag The QR code tag for which public data is returned.
     *
     * @param language The user selected language.
     * @return The public data to be used by the public B2C page.
     */
    public ApiQRTagPublic getQRTagPublicData(String qrTag, Boolean withHistory, Language language) throws ApiException {

        ApiQRTagPublic apiQRTagPublic = new ApiQRTagPublic();

        // Get the top level Stock order for the provided QR tag (the Stock order that is
        // not used as an input transaction in other Processing orders)
        StockOrder topLevelStockOrder = em.createNamedQuery("StockOrder.getTopLevelStockOrdersForQrTag", StockOrder.class)
                .setParameter("qrTag", qrTag)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (topLevelStockOrder != null) {

            // Get and set the orderId
            String orderId;
            if (topLevelStockOrder.getProductOrder() != null) {
                orderId = topLevelStockOrder.getProductOrder().getOrderId();
            } else {
                orderId = topLevelStockOrder.getOrderId();
            }

            apiQRTagPublic.setOrderId(orderId);
            apiQRTagPublic.setQrTag(topLevelStockOrder.getQrCodeTag());

            // Set the aggregated history (if requested from the API call)
            if (BooleanUtils.isTrue(withHistory)) {

                ApiHistoryTimeline historyTimeline = new ApiHistoryTimeline();
                apiQRTagPublic.setHistoryTimeline(historyTimeline);

                // Get the history for the selected Stock order
                ApiStockOrderHistory stockOrderHistory = getStockOrderAggregatedHistoryList(topLevelStockOrder.getId(), language, false);

                // Map the Stock order history timeline to the public QR code tag data
                historyTimeline.setItems(stockOrderHistory.getTimelineItems()
                        .stream()
                        .map(processing -> {
                            ApiHistoryTimelineItem historyTimelineItem = new ApiHistoryTimelineItem();

                            if (processing.getProcessingOrder() != null) {

                                // If processing item has no public timeline label defined, skip that processing
                                if (StringUtils.isNotBlank(processing.getProcessingOrder().getProcessingAction().getPublicTimelineLabel())) {
                                    historyTimelineItem.setType(processing.getProcessingOrder().getProcessingAction().getType().toString());
                                    historyTimelineItem.setName(processing.getProcessingOrder().getProcessingAction().getName());
                                    historyTimelineItem.setDate(processing.getProcessingOrder().getProcessingDate());
                                    
                                    processing.getProcessingOrder().getTargetStockOrders().stream().findAny().ifPresent(t -> {
                                        historyTimelineItem.setLatitude(t.getFacility().getFacilityLocation().getLatitude());
                                        historyTimelineItem.setLongitude(t.getFacility().getFacilityLocation().getLongitude());
                                    });
                                    processing.getProcessingOrder().getTargetStockOrders().stream().findAny()
                                            .ifPresent(tSO -> historyTimelineItem.setLocation(tSO.getFacility().getName()));
                                    
                                }
                            } else {

                                processing.getPurchaseOrders().stream().findFirst().ifPresent(po -> {
                                    historyTimelineItem.setDate(po.getProductionDate());
                                    historyTimelineItem.setLocation(po.getFacility().getName());

                                    apiQRTagPublic.setProducerName(po.getFacility().getCompany().getName());
                                });
                            }

                            return historyTimelineItem;
                        })
                        .filter(historyTimelineItem -> historyTimelineItem.getDate() != null)
                        .collect(Collectors.toList()));
            }
        }

        return apiQRTagPublic;
    }

    /**
     * Returns the history (the chain of stock orders from top to bottom) for the provided Stock order ID.
     *
     * @param id The Stock order ID.
     * @param language User selected language.
     * @param withDetails Should the response contain Stock and Processing order details.
     */
    public ApiStockOrderHistory getStockOrderAggregatedHistoryList(Long id, Language language, boolean withDetails) throws ApiException {

        StockOrder stockOrder = fetchEntity(id, StockOrder.class);

        ApiStockOrderHistory stockOrderHistory = new ApiStockOrderHistory();

        // Recursively add history, starting from depth 0
        List<ApiStockOrderHistoryTimelineItem> historyTimeline = addNextAggregationLevels(0, stockOrder, language);
        historyTimeline.sort(Comparator.comparingInt(ApiStockOrderHistoryTimelineItem::getDepth));

        // Prepare aggregated Purchase orders (group all into single history timeline item)
        ApiStockOrderHistoryTimelineItem aggregatedPurchaseOrders = new ApiStockOrderHistoryTimelineItem();

        stockOrderHistory.setTimelineItems(historyTimeline.stream().filter(timelineItem -> {
            if (timelineItem.getStockOrder() != null) {
                aggregatedPurchaseOrders.getPurchaseOrders().add(timelineItem.getStockOrder());
                return false;
            }
            return true;
        }).collect(Collectors.toList()));
        stockOrderHistory.getTimelineItems().add(aggregatedPurchaseOrders);

        // If details are not request end the processing (we don't need the Stock and Processing order data including input and output transactions)
        if (!withDetails) {
            return stockOrderHistory;
        }

        // Set the Stock order and Processing order for which the history is calculated (including the input transaction for the Processing order)
        stockOrderHistory.setStockOrder(StockOrderMapper.toApiStockOrderHistory(stockOrder, language));
        stockOrderHistory.setProcessingOrder(ProcessingOrderMapper.toApiProcessingOrderHistory(stockOrder.getProcessingOrder(), language));

        if (stockOrder.getProcessingOrder() != null) {

            // Set the input transactions for the Processing order
            stockOrderHistory.getProcessingOrder().setInputTransactions(
                    stockOrder.getProcessingOrder().getInputTransactions().stream()
                            .map(TransactionMapper::toApiTransactionHistory).collect(Collectors.toList()));

            // Set the sibling Stock orders
            stockOrderHistory.getProcessingOrder().setTargetStockOrders(
                    stockOrder.getProcessingOrder().getTargetStockOrders().stream().map(tSO -> {
                        ApiStockOrder apiStockOrder = new ApiStockOrder();
                        apiStockOrder.setId(tSO.getId());
                        apiStockOrder.setTotalQuantity(tSO.getTotalQuantity());
                        apiStockOrder.setMeasureUnitType(
                                MeasureUnitTypeMapper.toApiMeasureUnitTypeBase(tSO.getMeasurementUnitType()));
                        return apiStockOrder;
                    }).collect(Collectors.toList()));
        }

        // Set the output transaction for the chosen Stock order (the transactions where this Stock order was used as an input)
        List<Transaction> outputTransactions = findOutputTransactions(stockOrder);
        stockOrderHistory.setOutputTransactions(outputTransactions.stream().map(transaction -> {
            ApiTransaction apiTransaction = TransactionMapper.toApiTransactionHistory(transaction);
            transaction.getTargetProcessingOrder().getTargetStockOrders().stream().findAny().ifPresent(tSO -> {
                ApiStockOrder apiTSO = new ApiStockOrder();
                apiTSO.setId(tSO.getId());
                apiTransaction.setTargetStockOrder(apiTSO);
            });
            return apiTransaction;
        }).collect(Collectors.toList()));

        return stockOrderHistory;
    }

    private List<Transaction> findOutputTransactions(StockOrder stockOrder) {

        // Fetch the transactions where the provided Stock order was used as an input Stock order
        TypedQuery<Transaction> getTransactionsBySourceStockOrderQuery = em
                .createNamedQuery("Transaction.getOutputTransactionsByStockOrderId", Transaction.class)
                .setParameter("stockOrderId", stockOrder.getId());

        return getTransactionsBySourceStockOrderQuery.getResultList();
    }

    /***
     * Recursively adds next stock aggregation history list.
     *
     * @param currentDepth - aggregated history depth level
     * @param stockOrder - current stockOrder entity for searching next child nodes
     * @param language - language
     *
     * @return returns aggregated history list for next levels
     *
     */
    private List<ApiStockOrderHistoryTimelineItem> addNextAggregationLevels(int currentDepth,
                                                                StockOrder stockOrder, Language language) {

        if (stockOrder != null) {

            List<ApiStockOrderHistoryTimelineItem> historyTimeline = new ArrayList<>();

            ApiStockOrderHistoryTimelineItem nextHistory = new ApiStockOrderHistoryTimelineItem();

            // If we have Processing order defined, we are dealing with orders that involve
            // processing (in the other case we have Purchase order - special case)
            if (stockOrder.getProcessingOrder() != null && !stockOrder.getProcessingOrder().getInputTransactions().isEmpty()) {

                // Get the Stock order that were used as input when executing the Processing order
                List<StockOrder> inputStockOrders = stockOrder.getProcessingOrder().getInputTransactions()
                        .stream()
                        .filter(transaction -> !transaction.getStatus().equals(TransactionStatus.CANCELED))
                        .map(Transaction::getSourceStockOrder)
                        .collect(Collectors.toList());

                // Get the Stock orders that were created when executing the Processing order
                List<ApiStockOrder> targetStockOrders = stockOrder.getProcessingOrder().getTargetStockOrders()
                        .stream()
                        .map(siblingOrder -> StockOrderMapper.toApiStockOrderHistoryItem(siblingOrder, language))
                        .collect(Collectors.toList());

                nextHistory.setProcessingOrder(
                        ProcessingOrderMapper.toApiProcessingOrderHistory(stockOrder.getProcessingOrder(), language));
                nextHistory.getProcessingOrder().setTargetStockOrders(targetStockOrders);
                nextHistory.setDepth(currentDepth);

                // next recursion for every child element
                if (inputStockOrders.get(0).getSacNumber() != null) {

                    // if sac number present, proceed with only first item leaves
                    historyTimeline.addAll(
                            addNextAggregationLevels(currentDepth + 1, inputStockOrders.get(0), language));
                } else if (OrderType.TRANSFER_ORDER.equals(inputStockOrders.get(0).getOrderType())) {

                    // if transfer order, go through with first item leaves
                    historyTimeline.addAll(
                            addNextAggregationLevels(currentDepth + 1, inputStockOrders.get(0), language));
                } else {

                    // proceed recursion with all leaves
                    inputStockOrders.forEach(sourceOrder -> historyTimeline.addAll(
                            addNextAggregationLevels(currentDepth + 1, sourceOrder, language)));
                }

            } else {

                nextHistory.setStockOrder(StockOrderMapper.toApiStockOrderHistory(stockOrder, language));
                nextHistory.setDepth(currentDepth);
            }

            historyTimeline.add(nextHistory);

            return historyTimeline;

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
            apiStockOrder.setDamagedWeightDeduction(farmer.getDamagedWeightDeduction());
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

        // Facility needs to be provided
        if (apiStockOrder.getFacility() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Facility needs to be provided!");
        }

        // Semi-product or Final product needs to be provided
        if (apiStockOrder.getSemiProduct() == null && apiStockOrder.getFinalProduct() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Semi-product or Final product needs to be provided!");
        }

        entity.setOrderType(apiStockOrder.getOrderType());
        entity.setFacility(facilityService.fetchFacility(apiStockOrder.getFacility().getId()));
        entity.setCompany(entity.getFacility().getCompany());
        entity.setIdentifier(apiStockOrder.getIdentifier());
        entity.setPreferredWayOfPayment(apiStockOrder.getPreferredWayOfPayment());
        entity.setSacNumber(apiStockOrder.getSacNumber());
        entity.setProductionDate(apiStockOrder.getProductionDate());
        entity.setDeliveryTime(apiStockOrder.getDeliveryTime());
        entity.setComments(apiStockOrder.getComments());

        // Set the internal LOT name and the LOT prefix if we have Processing order provided
        entity.setInternalLotNumber(apiStockOrder.getInternalLotNumber());
        if (processingOrder != null) {
            entity.setLotPrefix(processingOrder.getProcessingAction().getPrefix());
        }

        // If provided set the Product order - this is provided when this Stock order was created as part of a Product order (batch Quote orders for final products)
        if (apiStockOrder.getProductOrder() != null && apiStockOrder.getProductOrder().getId() != null) {
            entity.setProductOrder(fetchEntity(apiStockOrder.getProductOrder().getId(), ProductOrder.class));
        }

        // Set price per unit for end customer (and currency) - this is used when placing Quote order for final products for a end customer
        entity.setPricePerUnitForEndCustomer(apiStockOrder.getPricePerUnitForEndCustomer());
        entity.setCurrencyForEndCustomer(apiStockOrder.getCurrencyForEndCustomer());

        // Set the consumer company customer (used in Quote orders for final products)
        if (apiStockOrder.getConsumerCompanyCustomer() != null && apiStockOrder.getConsumerCompanyCustomer().getId() != null) {
            entity.setConsumerCompanyCustomer(fetchEntity(apiStockOrder.getConsumerCompanyCustomer().getId(), CompanyCustomer.class));
        }

        // Set semi-product (used in processing)
        if (apiStockOrder.getSemiProduct() != null) {
            entity.setSemiProduct(semiProductService.fetchSemiProduct(apiStockOrder.getSemiProduct().getId()));
        }

        // Set final product (used in final product processing)
        if (apiStockOrder.getFinalProduct() != null) {
            entity.setFinalProduct(finalProductService.fetchFinalProduct(apiStockOrder.getFinalProduct().getId()));
        }

        entity.setOrganic(apiStockOrder.getOrganic());
        entity.setTare(apiStockOrder.getTare());
        entity.setWomenShare(apiStockOrder.getWomenShare());
        entity.setDamagedPriceDeduction(apiStockOrder.getDamagedPriceDeduction());
        entity.setDamagedWeightDeduction(apiStockOrder.getDamagedWeightDeduction());
        entity.setCurrency(apiStockOrder.getCurrency());

        // Calculate the quantities for this stock order accommodating all different cases of stock orders
        calculateQuantities(apiStockOrder, entity, processingOrder);

        // Set the measure unit from the contained semi-product
        if (entity.getSemiProduct() != null) {
            entity.setMeasurementUnitType(entity.getSemiProduct().getMeasurementUnitType());
        }

        // Set the measure unit from the contained final product
        if (entity.getFinalProduct() != null) {
            entity.setMeasurementUnitType(entity.getFinalProduct().getMeasurementUnitType());
        }

        // If entity is new and processing order is provided with processing action type 'GENERATE_QR_CODE', generate a new QR code tag for this stock order
        generateStockOrderQRCodeTag(entity, processingOrder);

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

                // On purchase order, Total quantity is calculated by total gross quantity and tare
                if (apiStockOrder.getTare() != null) {
                    apiStockOrder.setTotalQuantity(apiStockOrder.getTotalGrossQuantity().subtract(apiStockOrder.getTare()));
                } else {
                    apiStockOrder.setTotalQuantity(apiStockOrder.getTotalGrossQuantity());
                }
                if (apiStockOrder.getDamagedWeightDeduction() != null) {
                    apiStockOrder.setTotalQuantity(apiStockOrder.getTotalQuantity().subtract(apiStockOrder.getDamagedWeightDeduction()));
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

    public void calculateQuantities(ApiStockOrder apiStockOrder, StockOrder stockOrder, ProcessingOrder processingOrder) throws ApiException{

        if (apiStockOrder.getOrderType() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "OrderType needs to be provided!");
        }
        if (apiStockOrder.getTotalQuantity() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Total quantity cannot be null!");
        }
        if (apiStockOrder.getFulfilledQuantity() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Fulfilled quantity cannot be null!");
        }

        BigDecimal lastUsedQuantity = (stockOrder.getFulfilledQuantity() != null && stockOrder.getAvailableQuantity() != null)
                ? stockOrder.getFulfilledQuantity().subtract(stockOrder.getAvailableQuantity())
                : null;

        stockOrder.setTotalQuantity(apiStockOrder.getTotalQuantity());

        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:
            case PROCESSING_ORDER:
            case TRANSFER_ORDER:

                stockOrder.setFulfilledQuantity(stockOrder.getTotalQuantity());

                // For new StockOrders set available quantity to total quantity
                if (stockOrder.getId() == null) {
                    stockOrder.setAvailableQuantity(stockOrder.getFulfilledQuantity());
                }

                break;

            case GENERAL_ORDER:

                // Applies only for new Quote StockOrders
                if (stockOrder.getId() == null) {
                    stockOrder.setFulfilledQuantity(apiStockOrder.getFulfilledQuantity());
                    stockOrder.setAvailableQuantity(apiStockOrder.getAvailableQuantity());
                }
        }

        // If existing entity and processing order is provided edit the quantities
        if (stockOrder.getId() != null) {
            if (processingOrder != null) {

                // Calculate quantities based on input transactions
                List<Transaction> inputTxs = processingOrder.getInputTransactions();

                if (apiStockOrder.getOrderType() == OrderType.GENERAL_ORDER) {

                    if (!processingOrder.getTargetStockOrders().contains(stockOrder)) {

                        // Quote order is part of another StockOrder (as source)
                        stockOrder.setAvailableQuantity(stockOrder.getAvailableQuantity().subtract(calculateUsedQuantity(inputTxs, stockOrder.getId())));
                        stockOrder.setFulfilledQuantity(calculateFulfilledQuantity(inputTxs, stockOrder.getId()));

                    } else {
                        // When updating quote order itself
                        stockOrder.setFulfilledQuantity(calculateFulfilledQuantity(inputTxs, null));
                    }

                } else {
                    stockOrder.setAvailableQuantity(stockOrder.getFulfilledQuantity()
                            .subtract(lastUsedQuantity != null ? lastUsedQuantity : BigDecimal.ZERO)
                            .subtract(calculateUsedQuantity(inputTxs, stockOrder.getId())));
                }

            } else if (lastUsedQuantity != null) {
                stockOrder.setAvailableQuantity(apiStockOrder.getFulfilledQuantity().subtract(lastUsedQuantity));
            }
        }

        // Calculate if the total quantity of this stock order is within the expected range
        if (processingOrder != null) {

            ProcessingAction procAction = processingOrder.getProcessingAction();
            BigDecimal expectedTotalQuantityPerUnit = procAction.getEstimatedOutputQuantityPerUnit();

            if (procAction.getType().equals(ProcessingActionType.PROCESSING) &&
                    BooleanUtils.isFalse(procAction.getRepackedOutputs()) && expectedTotalQuantityPerUnit != null) {

                // Calculate the total input quantity (summed up input transactions from the processing order)
                List<Transaction> inputTxs = processingOrder.getInputTransactions();
                BigDecimal totalInputQuantity = inputTxs.stream()
                        .filter(t -> !t.getStatus().equals(TransactionStatus.CANCELED))
                        .map(Transaction::getOutputQuantity)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal inputUnitWeight = procAction.getInputSemiProduct().getMeasurementUnitType().getWeight();
                BigDecimal outputUnitWeight = procAction.getOutputSemiProduct().getMeasurementUnitType().getWeight();

                // Calculate the input quantity normalized in the output measuring unit (important when we have different input and output measure units)
                BigDecimal totalInputQuantityNormalized = totalInputQuantity.divide(inputUnitWeight,
                        RoundingMode.HALF_UP).divide(outputUnitWeight, RoundingMode.HALF_UP);

                // Calculate the expected total output quantity
                BigDecimal totalExpectedOutQuantity = totalInputQuantityNormalized.multiply(expectedTotalQuantityPerUnit);

                // Calculate the expected total output quantity allowed range (+/- 20% of the total expected output quantity)
                BigDecimal outQuantityRangeLow = totalExpectedOutQuantity
                        .multiply(new BigDecimal("0.8"))
                        .setScale(2, RoundingMode.HALF_UP);
                BigDecimal outQuantityRangeHigh = totalExpectedOutQuantity
                        .multiply(new BigDecimal("1.2"))
                        .min(totalInputQuantityNormalized)
                        .setScale(2, RoundingMode.HALF_UP);

                // Check if the total output quantity set in the stock order is within the allowed range
                BigDecimal totalQuantity = stockOrder.getTotalQuantity();
                stockOrder.setOutQuantityNotInRange(!(totalQuantity.compareTo(outQuantityRangeLow) >= 0 &&
                        totalQuantity.compareTo(outQuantityRangeHigh) <= 0));
            } else {

                stockOrder.setOutQuantityNotInRange(null);
            }
        }

        // Validate quantities
        if (stockOrder.getAvailableQuantity() != null) {

            if (stockOrder.getAvailableQuantity().compareTo(BigDecimal.ZERO) < 0) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity resulted in negative number.");
            }

            if (stockOrder.getFulfilledQuantity() != null) {
                if (stockOrder.getFulfilledQuantity().compareTo(stockOrder.getAvailableQuantity()) < 0) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Available quantity (" + stockOrder.getAvailableQuantity()
                            + ") cannot be bigger then fulfilled quantity (" + stockOrder.getFulfilledQuantity() + ").");
                }
                if (stockOrder.getFulfilledQuantity().compareTo(stockOrder.getTotalQuantity()) > 0) {
                    throw new ApiException(ApiStatus.VALIDATION_ERROR, "Fulfilled quantity (" + stockOrder.getFulfilledQuantity()
                            + ") cannot be bigger then total quantity (" + stockOrder.getTotalQuantity() + ").");
                }
            }
        }

        stockOrder.setAvailable(stockOrder.getAvailableQuantity() != null && stockOrder.getAvailableQuantity().compareTo(BigDecimal.ZERO) > 0);
        stockOrder.setIsOpenOrder(stockOrder.getOrderType() == OrderType.GENERAL_ORDER && stockOrder.getTotalQuantity().compareTo(stockOrder.getFulfilledQuantity()) > 0);
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
                balance = balance.subtract(payment.getAmount());

                if (payment.getPreferredWayOfPayment() != PreferredWayOfPayment.CASH_VIA_COLLECTOR) {
                    balance = balance.subtract(payment.getAmountPaidToTheCollector());
                }
            }
        }
        return balance;
    }

    private BigDecimal calculateFulfilledQuantity(List<Transaction> inputTransactions, Long stockOrderId){
        if (inputTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return inputTransactions.stream()
                .filter(t -> !t.getStatus().equals(TransactionStatus.CANCELED))
                .filter(t -> stockOrderId == null || stockOrderId.equals(t.getSourceStockOrder().getId()))
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

    private void generateStockOrderQRCodeTag(StockOrder entity, ProcessingOrder processingOrder) {

        if (entity.getId() == null && processingOrder != null && processingOrder.getProcessingAction().getType() == ProcessingActionType.GENERATE_QR_CODE) {
            entity.setQrCodeTag(UUID.randomUUID().toString());
            entity.setQrCodeTagFinalProduct(processingOrder.getProcessingAction().getQrCodeForFinalProduct());
        }
    }

}
