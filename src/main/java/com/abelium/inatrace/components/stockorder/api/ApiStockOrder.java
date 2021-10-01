package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.components.stockorder.converters.SimpleDateConverter;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;

@Validated
public class ApiStockOrder extends ApiBaseEntity {

    @ApiModelProperty(value = "Stock order identifier", position = 1)
    public String identifier;

    @ApiModelProperty(value = "Timestamp indicates when stock order have been updated", position = 2)
    public Instant updateTimestamp;

    @ApiModelProperty(value = "User that has created StockOrder")
    public ApiUser createdBy;

    @ApiModelProperty(value = "User that has last updated StockOrder")
    public ApiUser updatedBy;

    @ApiModelProperty(value = "ID of the user who has created the stock order", position = 3)
    public Long creatorId;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Representative of producer user customer. E.g. collector.", position = 4)
    public ApiUserCustomer representativeOfProducerUserCustomer;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Id of the person who has produced the entry.", position = 5)
    public ApiUserCustomer producerUserCustomer;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Production location", position = 6)
    public ApiStockOrderLocation productionLocation;

//    @ApiModelProperty(value = "Certification", position = 6)
//    @Valid
//    public List<ApiCertification> certifications;

    @ApiModelProperty(value = "Semi product", position = 7)
    public ApiSemiProduct semiProduct;

    @ApiModelProperty(value = "Facility", position = 8)
    public ApiFacility facility;

    @ApiModelProperty(value = "Company", position = 9)
    public ApiCompany company;

    @ApiModelProperty(value = "Measurement unit", position = 10)
    public ApiMeasureUnitType measureUnitType;

    @ApiModelProperty(value = "Total quantity", position = 11)
    public Integer totalQuantity;

    @ApiModelProperty(value = "Fulfilled quantity", position = 12)
    public Integer fulfilledQuantity;

    @ApiModelProperty(value = "Available quantity", position = 13)
    public Integer availableQuantity;

    @ApiModelProperty(value = "Is stock available", position = 14)
    public Boolean isAvailable;

    @ApiModelProperty(value = "Production date", position = 15)
    @JsonSerialize(converter = SimpleDateConverter.Serialize.class)
    @JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    public Instant productionDate;

//    @ApiModelProperty(value = "Expiry date", position = 16)
//    public Instant expiryDate;

//    @ApiModelProperty(value = "Estimated delivery date", position = 17)
//    public Instant estimatedDeliveryDate;

    @ApiModelProperty(value = "Delivery time", position = 18)
    public Instant deliveryTime;

//    @ApiModelProperty(value = "Order ID", position = 19)
//    public Long orderId;

//    @ApiModelProperty(value = "Global order ID", position = 20)
//    public Long globalOrderId;

    @ApiModelProperty(value = "Price per unit", position = 21)
    public BigDecimal pricePerUnit;

//    @ApiModelProperty(value = "Sales price per unit", position = 22)
//    public Float salesPricePerUnit;

    @ApiModelProperty(value = "Currency", position = 23)
    public String currency;

//    @ApiModelProperty(value = "Sales currency", position = 24)
//    public String salesCurrency;

    @ApiModelProperty(value = "Is order of type PURCHASE_ORDER", position = 25)
    public Boolean isPurchaseOrder;

    @ApiModelProperty(value = "Order type", position = 26)
    public OrderType orderType;

    @ApiModelProperty(value = "Internal LOT number", position = 27)
    public String internalLotNumber;

//    @ApiModelProperty(value = "LOT number", position = 28)
//    public String lotNumber;

//    @ApiModelProperty(value = "LOT label", position = 29)
//    public String lotLabel;

//    @ApiModelProperty(value = "Screen size", position = 30)
//    public String screenSize;

//    @ApiModelProperty(value = "Comments", position = 31)
//    public String comments;

//    @ApiModelProperty(value = "Action type", position = 32)
//    public ApiActionType actionType;

    @ApiModelProperty(value = "Is women share", position = 33)
    private Boolean womenShare;

    @ApiModelProperty(value = "Cost", position = 34)
    public BigDecimal cost;

    @ApiModelProperty(value = "Paid", position = 35)
    public BigDecimal paid;

    @ApiModelProperty(value = "Balance", position = 36)
    public BigDecimal balance;

    // Relevant only for order type: STOCK_ORDER
//    @ApiModelProperty(value = "Input transactions for stock order. Read only.")
//    public List<ApiTransaction> apiTransactions;

//    @ApiModelProperty(value = "Date when product has been started drying", position = 37)
//    public Instant startOfDrying;

//    @ApiModelProperty(value = "Client company", position = 38)
//    public ApiCompany client;

//    @ApiModelProperty(value = "Flavour profile", position = 39)
//    public String flavourProfile;

//    @ApiModelProperty(value = "Processing action", position = 40)
//    public ApiProcessingAction processingAction;

    @ApiModelProperty(value = "Preferred way of payment", position = 41)
    private PreferredWayOfPayment preferredWayOfPayment;

//    @ApiModelProperty(value = "SAC number", position = 42)
//    private Integer sacNumber;

//    @ApiModelProperty(value = "Is order open", position = 43)
//    private Boolean isOpenOrder;

//    @ApiModelProperty(value = "Quote facility", position = 44)
//    private Facility quoteFacility;

//    @ApiModelProperty(value = "Quote Company", position = 45)
//    private Company quoteCompany;

//    @ApiModelProperty(value = "Price per unit for owner", position = 46)
//    private BigDecimal pricePerUnitForOwner;

//    @ApiModelProperty(value = "Price per unit for buyer", position = 47)
//    private BigDecimal pricePerUnitForBuyer;

//    @ApiModelProperty(value = "Exchange rate at buyer", position = 48)
//    private BigDecimal exchangeRateAtBuyer;

//    @ApiModelProperty(value = "Price per unit for end customer", position = 49)
//    private BigDecimal pricePerUnitForEndCustomer;

//    @ApiModelProperty(value = "Exchange rate at end customer", position = 50)
//    private BigDecimal exchangeRateAtEndCustomer;

//    @ApiModelProperty(value = "Cupping result", position = 51)
//    private String cuppingResult;

//    @ApiModelProperty(value = "Cupping grade", position = 52)
//    private String cuppingGrade;

//    @ApiModelProperty(value = "Cupping flavour", position = 53)
//    private String cuppingFlavour;

//    @ApiModelProperty(value = "Roasting date", position = 54)
//    private Instant roastingDate;

//    @ApiModelProperty(value = "Roasting profile", position = 55)
//    private String roastingProfile;

//    @ApiModelProperty(value = "Shipper details", position = 56)
//    private String shipperDetails;

//    @ApiModelProperty(value = "Carrier details", position = 57)
//    private String carrierDetails;

//    @ApiModelProperty(value = "Part of loading", position = 58)
//    private String portOfLoading;

//    @ApiModelProperty(value = "Port od discharge", position = 59)
//    private String portOfDischarge;

//    @ApiModelProperty(value = "Date of end delivery", position = 60)
//    private Instant dateOfEndDelivery;

//    @ApiModelProperty(value = "Required women's coffee", position = 61)
//    private Boolean requiredWomensCoffee;

//    @ApiModelProperty(value = "Shipper at date from origin port", position = 62)
//    private Instant shippedAtDateFromOriginPort;

//    @ApiModelProperty(value = "Arrived at date to destination port", position = 63)
//    private Instant arrivedAtDateToDestinationPort;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public ApiUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ApiUser createdBy) {
        this.createdBy = createdBy;
    }

    public ApiUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ApiUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public ApiUserCustomer getRepresentativeOfProducerUserCustomer() {
        return representativeOfProducerUserCustomer;
    }

    public void setRepresentativeOfProducerUserCustomer(ApiUserCustomer representativeOfProducerUserCustomer) {
        this.representativeOfProducerUserCustomer = representativeOfProducerUserCustomer;
    }

    public ApiUserCustomer getProducerUserCustomer() {
        return producerUserCustomer;
    }

    public void setProducerUserCustomer(ApiUserCustomer producerUserCustomer) {
        this.producerUserCustomer = producerUserCustomer;
    }

    public ApiStockOrderLocation getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(ApiStockOrderLocation productionLocation) {
        this.productionLocation = productionLocation;
    }

    public ApiSemiProduct getSemiProduct() {
        return semiProduct;
    }

    public void setSemiProduct(ApiSemiProduct semiProduct) {
        this.semiProduct = semiProduct;
    }

    public ApiFacility getFacility() {
        return facility;
    }

    public void setFacility(ApiFacility facility) {
        this.facility = facility;
    }

    public ApiCompany getCompany() {
        return company;
    }

    public void setCompany(ApiCompany company) {
        this.company = company;
    }

    public ApiMeasureUnitType getMeasureUnitType() {
        return measureUnitType;
    }

    public void setMeasureUnitType(ApiMeasureUnitType measureUnitType) {
        this.measureUnitType = measureUnitType;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getFulfilledQuantity() {
        return fulfilledQuantity;
    }

    public void setFulfilledQuantity(Integer fulfilledQuantity) {
        this.fulfilledQuantity = fulfilledQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getPurchaseOrder() {
        return isPurchaseOrder;
    }

    public void setPurchaseOrder(Boolean purchaseOrder) {
        isPurchaseOrder = purchaseOrder;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getInternalLotNumber() {
        return internalLotNumber;
    }

    public void setInternalLotNumber(String internalLotNumber) {
        this.internalLotNumber = internalLotNumber;
    }

    public Boolean getWomenShare() {
        return womenShare;
    }

    public void setWomenShare(Boolean womenShare) {
        this.womenShare = womenShare;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public PreferredWayOfPayment getPreferredWayOfPayment() {
        return preferredWayOfPayment;
    }

    public void setPreferredWayOfPayment(PreferredWayOfPayment preferredWayOfPayment) {
        this.preferredWayOfPayment = preferredWayOfPayment;
    }
}
