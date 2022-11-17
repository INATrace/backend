package com.abelium.inatrace.components.groupstockorder;

public class GroupStockOrderQueryRequest {

    public Long facilityId;
    public Boolean availableOnly;

    public GroupStockOrderQueryRequest(
            Long facilityId,
            Boolean availableOnly
    ) {
        this.facilityId = facilityId;
        this.availableOnly = availableOnly;
    }
}
