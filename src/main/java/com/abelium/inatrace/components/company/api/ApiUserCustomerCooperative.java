package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserCustomerCooperative extends ApiBaseEntity {

    @Schema(description = "User customer")
    public ApiUserCustomer userCustomer;

    @Schema(description = "Company")
    public ApiCompany company;

    @Schema(description = "User customer type (collector, farmer)")
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
