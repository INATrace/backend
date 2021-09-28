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
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
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

    public ApiPaginatedList<ApiStockOrder> getStockOrderList(ApiPaginatedRequest request) {
        return PaginationTools.createPaginatedResponse(em, request, () -> stockOrderQueryObject(request), StockOrderMapper::toApiStockOrder);
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderListByCompanyId(ApiPaginatedRequest request, Long companyId) {
        return PaginationTools.createPaginatedResponse(em, request, () -> stockOrderByCompanyIdQueryObject(request, companyId), StockOrderMapper::toApiStockOrder);
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderListByFacilityId(ApiPaginatedRequest request, Long facilityId) {
        return PaginationTools.createPaginatedResponse(em, request, () -> stockOrderByFacilityIdQueryObject(request, facilityId), StockOrderMapper::toApiStockOrder);
    }

    private StockOrder stockOrderQueryObject(ApiPaginatedRequest request) {

        StockOrder stockOrderProxy = Torpedo.from(StockOrder.class);

        // -> FILTERS

        QueryTools.orderBy(request.sort, stockOrderProxy.getId());

        return stockOrderProxy;
    }

    private StockOrder stockOrderByCompanyIdQueryObject(ApiPaginatedRequest request, Long companyId){
        StockOrder soProxy = Torpedo.from(StockOrder.class);
        OnGoingLogicalCondition condition = Torpedo.condition();
        condition = condition.and(soProxy.getCompany().getId()).eq(companyId);
        Torpedo.where(condition);

        // -> FILTERS

        QueryTools.orderBy(request.sort, soProxy.getId());

        return soProxy;
    }

    private StockOrder stockOrderByFacilityIdQueryObject(ApiPaginatedRequest request, Long facilityId){
        StockOrder soProxy = Torpedo.from(StockOrder.class);
        OnGoingLogicalCondition condition = Torpedo.condition();
        condition = condition.and(soProxy.getFacility().getId()).eq(facilityId);
        Torpedo.where(condition);

        // -> FILTERS

        QueryTools.orderBy(request.sort, soProxy.getId());

        return soProxy;
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
