package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.stockorder.DocumentRequirement;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderLocation;
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
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@Lazy
@Service
public class StockOrderService extends BaseService {

    @Autowired
    private FacilityService facilityService;

    public ApiStockOrder getStockOrder(long id) throws ApiException {
        return StockOrderMapper.toApiStockOrder(fetchEntity(id, StockOrder.class));
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderList(ApiPaginatedRequest request,
                                                             Long companyId,
                                                             Long facilityId,
                                                             Boolean isOpenBalanceOnly,
                                                             Boolean isWomenShare,
                                                             PreferredWayOfPayment wayOfPayment,
                                                             Instant productionDateStart,
                                                             Instant productionDateEnd,
                                                             String producerUserCustomerName) {
        return PaginationTools.createPaginatedResponse(em, request,
                () -> stockOrderQueryObject(
                        request,
                        companyId,
                        facilityId,
                        isOpenBalanceOnly,
                        isWomenShare,
                        wayOfPayment,
                        productionDateStart,
                        productionDateEnd,
                        producerUserCustomerName
                ), StockOrderMapper::toApiStockOrder);
    }

    private StockOrder stockOrderQueryObject(ApiPaginatedRequest request,
                                             Long companyId,
                                             Long facilityId,
                                             Boolean isOpenBalanceOnly,
                                             Boolean isWomenShare,
                                             PreferredWayOfPayment wayOfPayment,
                                             Instant productionDateStart,
                                             Instant productionDateEnd,
                                             String producerUserCustomerName) {

        StockOrder stockOrderProxy = Torpedo.from(StockOrder.class);
        OnGoingLogicalCondition condition = Torpedo.condition();

        // Only present when listing by facility or company
        if(companyId != null)
            condition = condition.and(stockOrderProxy.getCompany().getId()).eq(companyId);
        else if (facilityId != null)
            condition = condition.and(stockOrderProxy.getFacility().getId()).eq(facilityId);


        // Query parameter filters
        if(isOpenBalanceOnly != null && isOpenBalanceOnly)
            condition.and(stockOrderProxy.getCost()).isNotNull()
                    .and(stockOrderProxy.getPaid()).isNotNull()
                    .and(stockOrderProxy.getCost()).gt(stockOrderProxy.getPaid());
        if(isWomenShare != null)
            condition.and(stockOrderProxy.getWomenShare()).eq(isWomenShare);
        if(wayOfPayment != null)
            condition.and(stockOrderProxy.getPreferredWayOfPayment()).eq(wayOfPayment);
        if(productionDateStart != null)
            condition.and(stockOrderProxy.getProductionDate()).gte(productionDateStart);
        if(productionDateEnd != null)
            condition.and(stockOrderProxy.getProductionDate()).lte(productionDateEnd);
        if(producerUserCustomerName != null) // Search by farmers name (query)
            condition.and(stockOrderProxy.getProducerUserCustomer().getName()).like().startsWith(producerUserCustomerName);

        Torpedo.where(condition);

        // Order by
        QueryTools.orderBy(request.sort, stockOrderProxy.getId());

        return stockOrderProxy;
    }

    @Transactional
    public ApiBaseEntity createOrUpdateStockOrder(ApiStockOrder apiStockOrder) throws ApiException {

        StockOrder entity;

        if (apiStockOrder.getId() != null) {
            entity = fetchEntity(apiStockOrder.getId(), StockOrder.class);
        } else {
            entity = new StockOrder();
            entity.setCreatorId(apiStockOrder.getCreatorId());
        }

        // Validation of required fields
        if(apiStockOrder.getOrderType() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Order type needs to be provided!");
        if(apiStockOrder.getFacility() == null)
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Facility.id needs to be provided!");

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
        entity.setPreferredWayOfPayment(apiStockOrder.getPreferredWayOfPayment());
        entity.setProductionDate(apiStockOrder.getProductionDate());
        entity.setSemiProduct(fetchEntity(apiStockOrder.getSemiProduct().getId(), SemiProduct.class));
        entity.setWomenShare(apiStockOrder.getWomenShare());

        entity.setAvailable(entity.getAvailableQuantity() > 0);

        // Production location
        ApiStockOrderLocation apiProdLocation = apiStockOrder.getProductionLocation();
        if(apiProdLocation != null) {

            StockOrderLocation stockOrderLocation = fetchEntityOrDefault(apiProdLocation.getId(), StockOrderLocation.class, null);

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

                entity.setDocumentRequirements(apiStockOrder.documentRequirements
                        .stream()
                        .filter(Objects::nonNull)
                        .map(apiDoc -> {
                            DocumentRequirement doc;
                            try {
                                doc = fetchEntity(apiDoc.getId(), DocumentRequirement.class);
                            } catch (ApiException e) {
                                doc = new DocumentRequirement();
                            }
                            doc.setName(apiDoc.getName());
                            doc.setDescription(apiDoc.getDescription());
                            doc.setIsRequired(apiDoc.getRequired());
                            // doc.setScoreTarget();
                            // doc.setFields();
                            // doc.setScoreTarget();
                            return doc;
                        }).collect(Collectors.toList()));

                // Required
                if(apiStockOrder.getProducerUserCustomer() == null)
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Producer user customer is required for purchase orders!");

                entity.setProducerUserCustomer(fetchEntity(apiStockOrder.getProducerUserCustomer().getId(), UserCustomer.class));
                entity.setPurchaseOrder(true);

                // Optional
                if(apiStockOrder.getRepresentativeOfProducerUserCustomer() != null)
                    entity.setRepresentativeOfProducerUserCustomer(fetchEntity(apiStockOrder.getRepresentativeOfProducerUserCustomer().getId(), UserCustomer.class));

                break;
            case SALES_ORDER:
            case GENERAL_ORDER:
            case TRANSFER_ORDER:
            case PROCESSING_ORDER:
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

    private <E> E fetchEntity(Long id, Class<E> entityClass) throws ApiException {

        E object = Queries.get(em, entityClass, id);
        if (object == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid " + entityClass.getSimpleName() + " ID");
        }
        return object;
    }

    private <E> E fetchEntityOrDefault(Long id, Class<E> entityClass, E defaultValue) {
        E object = Queries.get(em, entityClass, id);
        return object == null ? defaultValue : object;
    }

}
