package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderActivityProof;
import com.abelium.inatrace.db.entities.stockorder.StockOrderLocation;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
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
    public ApiBaseEntity createOrUpdateStockOrder(ApiStockOrder apiStockOrder, Long userId) throws ApiException {

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
        entity.setAvailableQuantity(apiStockOrder.getAvailableQuantity());
        entity.setFulfilledQuantity(apiStockOrder.getFulfilledQuantity());
        entity.setTotalQuantity(apiStockOrder.getTotalQuantity());
        entity.setBalance(apiStockOrder.getBalance());
        entity.setPaid(apiStockOrder.getPaid());
        entity.setCost(apiStockOrder.getCost());
        entity.setCurrency(apiStockOrder.getCurrency());
        entity.setPricePerUnit(apiStockOrder.getPricePerUnit());
        entity.setPreferredWayOfPayment(apiStockOrder.getPreferredWayOfPayment());
        entity.setSacNumber(apiStockOrder.getSacNumber());
        entity.setProductionDate(apiStockOrder.getProductionDate());
        entity.setInternalLotNumber(apiStockOrder.getInternalLotNumber());
        entity.setWomenShare(apiStockOrder.getWomenShare());
        entity.setDeliveryTime(apiStockOrder.getDeliveryTime());
        entity.setAvailable(entity.getAvailableQuantity() > 0);
        entity.setSemiProduct(fetchEntity(apiStockOrder.getSemiProduct().getId(), SemiProduct.class));

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
            // stockOrderLocation.setAddress();
            entity.setProductionLocation(stockOrderLocation);
        }

        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:

                // Required
                if(apiStockOrder.getProducerUserCustomer() == null)
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Producer user customer is required for purchase orders!");

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

    private <E> E fetchEntity(Long id, Class<E> entityClass) throws ApiException {

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

}
