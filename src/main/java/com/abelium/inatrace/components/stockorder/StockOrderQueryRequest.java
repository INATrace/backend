package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.Instant;

public class StockOrderQueryRequest {
    
    public StockOrderQueryRequest() {}

    public StockOrderQueryRequest(Long facilityId, Long semiProductId, Boolean isAvailable) {
        this.facilityId = facilityId;
        this.semiProductId = semiProductId;
        this.isAvailable = isAvailable;
    }
    
    public StockOrderQueryRequest(Long companyId,
                                  Long facilityId,
                                  Boolean isOpenBalanceOnly,
                                  Boolean isWomenShare,
                                  PreferredWayOfPayment wayOfPayment,
                                  Instant productionDateStart,
                                  Instant productionDateEnd,
                                  String producerUserCustomerName) {
        this.companyId = companyId;
        this.facilityId = facilityId;
        this.isOpenBalanceOnly = isOpenBalanceOnly;
        this.isWomenShare = isWomenShare;
        this.wayOfPayment = wayOfPayment;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
        this.producerUserCustomerName = producerUserCustomerName;
    }

    public Long companyId;
    public Long facilityId;
    public Long semiProductId;
    public Boolean isOpenBalanceOnly;
    public Boolean isWomenShare;
    public PreferredWayOfPayment wayOfPayment;
    public Instant productionDateStart;
    public Instant productionDateEnd;
    public String producerUserCustomerName;
    public Boolean isAvailable;
}
