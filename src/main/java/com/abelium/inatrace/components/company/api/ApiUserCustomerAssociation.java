package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserCustomerAssociation extends ApiBaseEntity {

    @Schema(description = "User customer")
    public ApiUserCustomer userCustomer;

    @Schema(description = "Company")
    public ApiCompany company;

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
}
