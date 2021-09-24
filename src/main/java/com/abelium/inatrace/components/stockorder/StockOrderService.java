package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.facility.FacilityService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;

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

    @Transactional
    public ApiBaseEntity createOrUpdateStockOrder(ApiStockOrder apiStockOrder) throws ApiException {

        // TODO: ApiStockOrderLocation

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

        entity.setAvailableQuantity(apiStockOrder.getAvailableQuantity());
        entity.setFulfilledQuantity(apiStockOrder.getFulfilledQuantity());
        entity.setTotalQuantity(apiStockOrder.getTotalQuantity());
        entity.setBalance(apiStockOrder.getBalance());
        entity.setCost(apiStockOrder.getCost());
        entity.setCurrency(apiStockOrder.getCurrency());
        entity.setPreferredWayOfPayment(apiStockOrder.getPreferredWayOfPayment());
        entity.setProductionDate(apiStockOrder.getProductionDate());
        entity.setSemiProduct(entity.getSemiProduct());
        entity.setWomenShare(apiStockOrder.getWomenShare());

        entity.setAvailable(entity.getAvailableQuantity() > 0);


        switch (apiStockOrder.getOrderType()) {
            case PURCHASE_ORDER:

                // TODO: Map the following fields, if required?
                // inputTransactions
                // outputTransactions
                // inputOrders
                // triggerOrders
                // [!] documentRequirements

                // Required
                if(apiStockOrder.getProducerUserCustomer() == null)
                    throw new ApiException(ApiStatus.INVALID_REQUEST, "Producer user customer is required for purchase orders!");

                entity.setProducerUserCustomer(fetchUserCustomer(apiStockOrder.getProducerUserCustomer().getId()));
                entity.setPurchaseOrder(true);

                // Optional
                if(apiStockOrder.getRepresentativeOfProducerCustomer() != null)
                    entity.setRepresentativeOfProducerCustomer(fetchUserCustomer(apiStockOrder.getRepresentativeOfProducerCustomer().getId()));

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

    private StockOrder stockOrderQueryObject(ApiPaginatedRequest request) {

        StockOrder stockOrderProxy = Torpedo.from(StockOrder.class);
        // if ("FILTER".equals(request.sortBy))
        QueryTools.orderBy(request.sort, stockOrderProxy.getId());

        return stockOrderProxy;
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


}
