package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.TimeDateUtil;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
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
        return StockOrderMapper.toApiStockOrder(fetchStockOrder(id));
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
            entity = fetchStockOrder(apiStockOrder.getId());
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
        entity.setProductionDate(TimeDateUtil.toInstant(apiStockOrder.getProductionDate(), TimeDateUtil.SIMPLE_DATE_FORMAT));
        entity.setSemiProduct(entity.getSemiProduct());
        entity.setWomenShare(apiStockOrder.getWomenShare());

        entity.setAvailable(entity.getAvailableQuantity() > 0);

        // Production location
        ApiStockOrderLocation apiProdLocation = apiStockOrder.getProductionLocation();
        if(apiProdLocation != null) {

            StockOrderLocation stockOrderLocation = fetchStockOrderLocationOrNull(apiProdLocation.getId());

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
                                doc = fetchDocumentRequirement(apiDoc.getId());
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

                entity.setProducerUserCustomer(fetchUserCustomer(apiStockOrder.getProducerUserCustomer().getId()));
                entity.setPurchaseOrder(true);

                // Optional
                if(apiStockOrder.getRepresentativeOfProducerUserCustomer() != null)
                    entity.setRepresentativeOfProducerUserCustomer(fetchUserCustomer(apiStockOrder.getRepresentativeOfProducerUserCustomer().getId()));

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
        StockOrder stockOrder = fetchStockOrder(id);
        em.remove(stockOrder);
    }

    private StockOrder fetchStockOrder(Long id) throws ApiException {

        StockOrder stockOrder = Queries.get(em, StockOrder.class, id);
        if (stockOrder == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid stock order ID");
        }
        return stockOrder;
    }

    private UserCustomer fetchUserCustomer(Long id) throws ApiException {
        UserCustomer pc = Queries.get(em, UserCustomer.class, id);
        if (pc == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user customer ID");
        }
        return pc;
    }

    private DocumentRequirement fetchDocumentRequirement(Long id) throws ApiException {
        DocumentRequirement dr = Queries.get(em, DocumentRequirement.class, id);
        if (dr == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid DocumentRequirement ID");
        }
        return dr;
    }

    private StockOrderLocation fetchStockOrderLocationOrNull(Long id){
        return Queries.get(em, StockOrderLocation.class, id);
    }

}
