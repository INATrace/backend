package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomerLocation;
import com.abelium.inatrace.components.product.ProductApiTools;
import com.abelium.inatrace.db.entities.common.UserCustomer;

public class UserCustomerMapper {

    public static ApiUserCustomer toApiUserCustomerBase(UserCustomer entity) {

        if (entity == null) return null;

        ApiUserCustomer apiUserCustomer = new ApiUserCustomer();
        apiUserCustomer.setId(entity.getId());
        apiUserCustomer.setName(entity.getName());
        apiUserCustomer.setSurname(entity.getSurname());
        apiUserCustomer.setType(entity.getType());

        return apiUserCustomer;
    }

    public static ApiUserCustomer toApiUserCustomer(UserCustomer entity) {

        ApiUserCustomer apiUserCustomer = toApiUserCustomerBase(entity);
        if (apiUserCustomer == null) {
            return null;
        }

        apiUserCustomer.setBank(ProductApiTools.toApiBankInformation(entity.getBank()));

        if (entity.getUserCustomerLocation() != null) {
            apiUserCustomer.setLocation(new ApiUserCustomerLocation());
            apiUserCustomer.getLocation()
                    .setAddress(AddressMapper.toApiAddress(entity.getUserCustomerLocation().getAddress()));
        }

        return apiUserCustomer;
    }

}
