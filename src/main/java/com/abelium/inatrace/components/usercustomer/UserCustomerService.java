package com.abelium.inatrace.components.usercustomer;

import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.usercustomer.api.ApiUserCustomer;
import com.abelium.inatrace.components.usercustomer.mappers.UserCustomerMapper;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

@Service
@Lazy
public class UserCustomerService extends BaseService {

    public ApiUserCustomer getUserCustomer(long id) throws ApiException {
        return UserCustomerMapper.toApiUserCustomerBase(fetchEntity(id, UserCustomer.class));
    }

    public ApiPaginatedList<ApiUserCustomer> getUserCustomerList(ApiPaginatedRequest request,
                                                                 UserCustomerQueryRequest queryRequest) {

        return PaginationTools.createPaginatedResponse(em, request,
                () -> userCustomerQueryObject(request, queryRequest), UserCustomerMapper::toApiUserCustomerBase);
    }

    private UserCustomer userCustomerQueryObject(ApiPaginatedRequest request, UserCustomerQueryRequest queryRequest) {

        UserCustomer userCustomerProxy = Torpedo.from(UserCustomer.class);
        OnGoingLogicalCondition condition = Torpedo.condition();

        // Query filters
        if (queryRequest.companyId != null)
            condition.and(userCustomerProxy.getCompany()).isNotNull()
                    .and(userCustomerProxy.getCompany().getId()).eq(queryRequest.companyId);
        if (queryRequest.userCustomerType != null)
            condition.and(userCustomerProxy.getType()).eq(queryRequest.userCustomerType);

        Torpedo.where(condition);

        // Sort
        QueryTools.orderBy(request.sort, userCustomerProxy.getId());

        return userCustomerProxy;
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
