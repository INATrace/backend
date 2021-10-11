package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiCompany;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserCustomerAssociation extends ApiBaseEntity {

    @ApiModelProperty(value = "User customer", position = 0)
    public ApiUserCustomer userCustomer;

    @ApiModelProperty(value = "Company", position = 1)
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
