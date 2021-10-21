package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiPaginatedRequest;

public class FinalProductQueryRequest extends ApiPaginatedRequest {

    FinalProductQueryRequest() {
    }

    FinalProductQueryRequest(Long productId) {
        this.productId = productId;
    }

    public Long productId;

}
