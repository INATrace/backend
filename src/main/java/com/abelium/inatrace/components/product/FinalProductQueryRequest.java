package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public class FinalProductQueryRequest extends ApiPaginatedRequest {

    @Parameter
    public Long productId;

    FinalProductQueryRequest() {
    }

    FinalProductQueryRequest(Long productId) {
        this.productId = productId;
    }

}
