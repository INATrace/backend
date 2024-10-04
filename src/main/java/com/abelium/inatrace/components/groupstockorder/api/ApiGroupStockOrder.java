package com.abelium.inatrace.components.groupstockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiGroupStockOrder extends ApiBaseEntity {

    public ApiGroupStockOrder() {}
    public ApiGroupStockOrder(
            String groupedIds,
            LocalDate productionDate,
            String internalLotNumber,
            Long noOfSacs,
            OrderType orderType,
            String semiProductName,
            String finalProductName,
            BigDecimal totalQuantity,
            BigDecimal fulfilledQuantity,
            BigDecimal availableQuantity,
            String unitLabel,
            LocalDate deliveryTime,
            Instant updateTimestamp,
            Boolean isAvailable
    ) {
        setProductionDate(productionDate);
        setInternalLotNumber(internalLotNumber);
        setOrderType(orderType);
        setTotalQuantity(totalQuantity);
        setFulfilledQuantity(fulfilledQuantity);
        setAvailableQuantity(availableQuantity);
        setUnitLabel(unitLabel);
        setDeliveryTime(deliveryTime);
        setUpdateTimestamp(updateTimestamp);
        setAvailable(isAvailable);
        setSemiProductName(semiProductName);
        setNoOfSacs(noOfSacs);
        setFinalProductName(finalProductName);
        setGroupedIds(Arrays.stream(groupedIds.split(",")).map(Long::parseLong).collect(Collectors.toList()));
    }

    @Schema(description = "List of stock order ID's, belonging to this group")
    private List<Long> groupedIds;

    @Schema(description = "Production date")
    private LocalDate productionDate;

    @Schema(description = "Timestamp indicates when process order have been updated")
    private Instant updateTimestamp;

    @Schema(description = "Internal LOT number")
    private String internalLotNumber;

    @Schema(description = "Number of sacs (if the order was repackaged)")
    private Long noOfSacs;

    @Schema(description = "Order type")
    private OrderType orderType;

    @Schema(description = "Semi product name")
    private String semiProductName;

    @Schema(description = "Final product name")
    private String finalProductName;

    @Schema(description = "Total quantity")
    private BigDecimal totalQuantity;

    @Schema(description = "Fulfilled quantity")
    private BigDecimal fulfilledQuantity;

    @Schema(description = "Available quantity")
    private BigDecimal availableQuantity;

    @Schema(description = "Measurement unit")
    private String unitLabel;

    @Schema(description = "Delivery time")
    private LocalDate deliveryTime;

    @Schema(description = "Is stock available")
    private Boolean isAvailable;

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getInternalLotNumber() {
        return internalLotNumber;
    }

    public void setInternalLotNumber(String internalLotNumber) {
        this.internalLotNumber = internalLotNumber;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getSemiProductName() {
        return semiProductName;
    }

    public void setSemiProductName(String semiProductName) {
        this.semiProductName = semiProductName;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getFulfilledQuantity() {
        return fulfilledQuantity;
    }

    public void setFulfilledQuantity(BigDecimal fulfilledQuantity) {
        this.fulfilledQuantity = fulfilledQuantity;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Long getNoOfSacs() {
        return noOfSacs;
    }

    public void setNoOfSacs(Long noOfSacs) {
        this.noOfSacs = noOfSacs;
    }

    public String getFinalProductName() {
        return finalProductName;
    }

    public void setFinalProductName(String finalProductName) {
        this.finalProductName = finalProductName;
    }

    public List<Long> getGroupedIds() {
        return groupedIds;
    }

    public void setGroupedIds(List<Long> groupedIds) {
        this.groupedIds = groupedIds;
    }
}
