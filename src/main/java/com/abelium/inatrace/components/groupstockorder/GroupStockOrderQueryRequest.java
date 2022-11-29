package com.abelium.inatrace.components.groupstockorder;

public class GroupStockOrderQueryRequest {

    public Long facilityId;
    public Boolean availableOnly;
    public Boolean isPurchaseOrderOnly;
    public Long semiProducId;

    public GroupStockOrderQueryRequest(
            Long facilityId,
            Boolean availableOnly,
            Boolean isPurchaseOrderOnly,
            Long semiProducId
    ) {
        this.facilityId = facilityId;
        this.availableOnly = availableOnly;
        this.isPurchaseOrderOnly = isPurchaseOrderOnly;
        this.semiProducId = semiProducId;
    }
}
