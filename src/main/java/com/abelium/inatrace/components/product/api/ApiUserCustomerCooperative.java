package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.company.api.ApiCompany;
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
}
