package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.Instant;

public class StockOrderQueryRequest {
    
    public StockOrderQueryRequest() {}

    public StockOrderQueryRequest(Long facilityId,
                                  Long semiProductId,
                                  Boolean isAvailable,
                                  Boolean isWomenShare,
                                  Instant productionDateStart,
                                  Instant productionDateEnd) {
        this.facilityId = facilityId;
        this.semiProductId = semiProductId;
        this.isAvailable = isAvailable;
        this.isWomenShare = isWomenShare;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
    }
    
    public StockOrderQueryRequest(Long companyId,
                                  Long facilityId,
                                  Long farmerId,
                                  Boolean isOpenBalanceOnly,
                                  Boolean isPurchaseOrderOnly,
                                  Boolean isAvailable,
                                  Long semiProductId,
                                  Boolean isWomenShare,
                                  PreferredWayOfPayment wayOfPayment,
                                  OrderType orderType,
                                  Instant productionDateStart,
                                  Instant productionDateEnd,
                                  String producerUserCustomerName) {
        this.companyId = companyId;
        this.facilityId = facilityId;
        this.farmerId = farmerId;
        this.isOpenBalanceOnly = isOpenBalanceOnly;
        this.isPurchaseOrderOnly = isPurchaseOrderOnly;
        this.isAvailable = isAvailable;
        this.semiProductId = semiProductId;
        this.isWomenShare = isWomenShare;
        this.wayOfPayment = wayOfPayment;
        this.orderType = orderType;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
        this.producerUserCustomerName = producerUserCustomerName;
    }

    public Long companyId;
    public Long facilityId;
    public Long farmerId;
    public Long semiProductId;
    public Boolean isOpenBalanceOnly;
    public Boolean isPurchaseOrderOnly;
    public Boolean isWomenShare;
    public PreferredWayOfPayment wayOfPayment;
    public OrderType orderType;
    public Instant productionDateStart;
    public Instant productionDateEnd;
    public String producerUserCustomerName;
    public Boolean isAvailable;

}