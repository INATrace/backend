package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserCustomerCooperative extends ApiBaseEntity {

    @ApiModelProperty(value = "User customer", position = 0)
    public ApiUserCustomer userCustomer;

    @ApiModelProperty(value = "Company", position = 1)
    public ApiCompany company;

    @ApiModelProperty(value = "User customer type (collector, farmer)", position = 2)
    public UserCustomerType userCustomerType;

    public ApiUserCustomer getUserCustomer() {
        return userCustomer;
    }

    public void setUserCustomer(ApiUserCustomer userCustomer) {
        this.userCustomer = userCustomer;
    }

    public ApiCompany getCompany() {
        return company;
    }

    public void setCompany(ApiCompany company) {
        this.company = company;
    }

    public UserCustomerType getUserCustomerType() {
        return userCustomerType;
    }

    public void setUserCustomerType(UserCustomerType userCustomerType) {
        this.userCustomerType = userCustomerType;
    }
}
