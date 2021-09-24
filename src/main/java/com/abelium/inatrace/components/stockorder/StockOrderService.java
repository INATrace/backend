package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;

@Lazy
@Service
public class StockOrderService extends BaseService {

    public ApiStockOrder getStockOrder(long id) throws ApiException {
        return StockOrderMapper.toApiStockOrder(fetchStockOrder(id));
    }

    public ApiPaginatedList<ApiStockOrder> getStockOrderList(ApiPaginatedRequest request) {
        return PaginationTools.createPaginatedResponse(em, request, () -> stockOrderQueryObject(request), StockOrderMapper::toApiStockOrder);
    }

    @Transactional
    public ApiBaseEntity createOrUpdateStockOrder(ApiStockOrder apiStockOrder){

        ProcessingEvidenceField entity;

//        if (apiStockOrder.getId() != null) {
//            entity = fetchStockOrder(apiStockOrder.getId());
//        } else {
            entity = new ProcessingEvidenceField();
//        }

        // TODO: Populate entity

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


}
