package com.abelium.inatrace.components.facility.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import io.swagger.annotations.ApiModelProperty;

public class ApiFacilitySemiProduct extends ApiBaseEntity {

    @ApiModelProperty(value = "List of semi products")
    private ApiSemiProduct apiSemiProduct;

    public ApiSemiProduct getApiSemiProduct() {
        return apiSemiProduct;
    }

    public void setApiSemiProduct(ApiSemiProduct apiSemiProduct) {
        this.apiSemiProduct = apiSemiProduct;
    }
}
