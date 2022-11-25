package com.abelium.inatrace.components.groupstockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiGroupStockOrder extends ApiBaseEntity {

    public ApiGroupStockOrder() {}
    public ApiGroupStockOrder(
            String groupedIds,
            Instant productionDate,
            String internalLotNumber,
            Long noOfSacs,
            OrderType orderType,
            String semiProductName,
            String finalProductName,
            BigDecimal totalQuantity,
            BigDecimal fulfilledQuantity,
            BigDecimal availableQuantity,
            String unitLabel,
            Instant deliveryTime,
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

    @ApiModelProperty(value = "List of stock order ID's, belonging to this group")
    private List<Long> groupedIds;

    @ApiModelProperty(value = "Production date")
    @JsonSerialize(converter = SimpleDateConverter.Serialize.class)
    @JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    private Instant productionDate;

    @ApiModelProperty(value = "Timestamp indicates when process order have been updated")
    private Instant updateTimestamp;

    @ApiModelProperty(value = "Internal LOT number")
    private String internalLotNumber;

    @ApiModelProperty(value = "Number of sacs (if the order was repackaged)")
    private Long noOfSacs;

    @ApiModelProperty(value = "Order type")
    private OrderType orderType;

    @ApiModelProperty(value = "Semi product name")
    private String semiProductName;

    @ApiModelProperty(value = "Final product name")
    private String finalProductName;

    @ApiModelProperty(value = "Total quantity")
    private BigDecimal totalQuantity;

    @ApiModelProperty(value = "Fulfilled quantity")
    private BigDecimal fulfilledQuantity;

    @ApiModelProperty(value = "Available quantity")
    private BigDecimal availableQuantity;

    @ApiModelProperty(value = "Measurement unit")
    private String unitLabel;

    @ApiModelProperty(value = "Delivery time")
    private Instant deliveryTime;

    @ApiModelProperty(value = "Is stock available")
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

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Instant getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Instant productionDate) {
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
