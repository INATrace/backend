package com.abelium.inatrace.components.product.mapper;

import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.db.entities.common.UserCustomer;

public class UserCustomerMapper {

    public static ApiUserCustomer toApiUserCustomerBase(UserCustomer entity) {

        if(entity == null) return null;

        ApiUserCustomer apiUserCustomer = new ApiUserCustomer();
        apiUserCustomer.setId(entity.getId());
        apiUserCustomer.setName(entity.getName());
        apiUserCustomer.setSurname(entity.getSurname());
        apiUserCustomer.setType(entity.getType());

        return apiUserCustomer;
    }

}
