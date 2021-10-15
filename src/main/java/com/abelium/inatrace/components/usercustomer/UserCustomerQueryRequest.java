package com.abelium.inatrace.components.usercustomer;

import com.abelium.inatrace.types.UserCustomerType;

public class UserCustomerQueryRequest {

    UserCustomerQueryRequest(){}

    UserCustomerQueryRequest(Long companyId, UserCustomerType userCustomerType) {
        this.companyId = companyId;
        this.userCustomerType = userCustomerType;
    }

    public Long companyId;
    public UserCustomerType userCustomerType;

}
