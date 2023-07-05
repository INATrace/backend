package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import java.time.LocalDate;

public class StockOrderQueryRequest {

    public Long companyId;
    public Long facilityId;
    public Long quoteCompanyId;
    public Long quoteFacilityId;
    public Long farmerId;
    public Long representativeOfProducerUserCustomerId;
    public Long semiProductId;
    public Long finalProductId;
    public Long companyCustomerId;
    public OrderType orderType;
    public Boolean isOpenBalanceOnly;
    public Boolean isPurchaseOrderOnly;
    public Boolean isWomenShare;
    public Boolean organicOnly;
    public String internalLotName;
    public PreferredWayOfPayment wayOfPayment;
    public LocalDate productionDateStart;
    public LocalDate productionDateEnd;
    public String producerUserCustomerName;
    public Boolean isAvailable;
    public Boolean isOpenOnly;

    public StockOrderQueryRequest() {}

    // Used for fetching Quote order (input and output)
    public StockOrderQueryRequest(Long companyId, Long facilityId, Long quoteCompanyId, Long quoteFacilityId, Long semiProductId, Long companyCustomerId,
                                  Boolean isOpenOnly) {

        this.companyId = companyId;
        this.facilityId = facilityId;
        this.quoteCompanyId = quoteCompanyId;
        this.quoteFacilityId = quoteFacilityId;
        this.semiProductId = semiProductId;
        this.companyCustomerId = companyCustomerId;
        this.isOpenOnly = isOpenOnly;
        this.orderType = OrderType.GENERAL_ORDER;
    }

    public StockOrderQueryRequest(Long facilityId,
                                  Long semiProductId,
                                  Long finalProductId,
                                  Boolean isAvailable,
                                  Boolean isWomenShare,
                                  Boolean organicOnly,
                                  String internalLotName,
                                  LocalDate productionDateStart,
                                  LocalDate productionDateEnd) {
        this.facilityId = facilityId;
        this.semiProductId = semiProductId;
        this.finalProductId = finalProductId;
        this.isAvailable = isAvailable;
        this.isWomenShare = isWomenShare;
        this.organicOnly = organicOnly;
        this.internalLotName = internalLotName;
        this.productionDateStart = productionDateStart;
        this.productionDateEnd = productionDateEnd;
    }
    
    public StockOrderQueryRequest(Long companyId,
                                  Long facilityId,
                                  Long farmerId,
                                  Long representativeOfProducerUserCustomerId,
                                  Boolean isOpenBalanceOnly,
                                  Boolean isPurchaseOrderOnly,
                                  Boolean isAvailable,
                                  Long semiProductId,
                                  Boolean isWomenShare,
                                  PreferredWayOfPayment wayOfPayment,
                                  OrderType orderType,
                                  LocalDate productionDateStart,
                                  LocalDate productionDateEnd,
                                  String producerUserCustomerName) {
        this.companyId = companyId;
        this.facilityId = facilityId;
        this.farmerId = farmerId;
        this.representativeOfProducerUserCustomerId = representativeOfProducerUserCustomerId;
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

}
