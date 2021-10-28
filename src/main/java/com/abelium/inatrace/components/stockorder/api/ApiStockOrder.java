package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.productorder.api.ApiProductOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiStockOrder extends ApiBaseEntity {

    @ApiModelProperty(value = "Stock order identifier", position = 1)
    private String identifier;

    @ApiModelProperty(value = "Timestamp indicates when stock order have been created")
    private Instant creationTimestamp;

    @ApiModelProperty(value = "Timestamp indicates when stock order have been updated", position = 2)
    private Instant updateTimestamp;

    @ApiModelProperty(value = "User that has created StockOrder")
    private ApiUser createdBy;

    @ApiModelProperty(value = "User that has last updated StockOrder")
    private ApiUser updatedBy;

    @ApiModelProperty(value = "ID of the user who has created the stock order", position = 3)
    private Long creatorId;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Representative of producer user customer. E.g. collector.", position = 4)
    private ApiUserCustomer representativeOfProducerUserCustomer;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Id of the person who has produced the entry.", position = 5)
    private ApiUserCustomer producerUserCustomer;

    // Relevant only for order type: PURCHASE_ORDER
    @ApiModelProperty(value = "Production location", position = 6)
    private ApiStockOrderLocation productionLocation;

//    @ApiModelProperty(value = "Certification", position = 6)
//    @Valid
//    public List<ApiCertification> certifications;

    @ApiModelProperty(value = "Activity proofs", position = 7)
    private List<ApiActivityProof> activityProofs;

    @ApiModelProperty(value = "Processing evidence fields stored values for this stock order")
    private List<ApiStockOrderEvidenceFieldValue> requiredEvidenceFieldValues;

    @ApiModelProperty(value = "Processing evidence types stored values for this stock order")
    private List<ApiStockOrderEvidenceTypeValue> requiredEvidenceTypeValues;

    @ApiModelProperty(value = "Other processing evidence documents - evidence types that can be provided but are not mandatory")
    private List<ApiStockOrderEvidenceTypeValue> otherEvidenceDocuments;

    @ApiModelProperty(value = "Semi product", position = 7)
    private ApiSemiProduct semiProduct;

    @ApiModelProperty(value = "Facility", position = 8)
    private ApiFacility facility;

    @ApiModelProperty(value = "Company", position = 9)
    private ApiCompany company;

    @ApiModelProperty(value = "Measurement unit", position = 10)
    private ApiMeasureUnitType measureUnitType;

    @ApiModelProperty(value = "Total quantity", position = 11)
    private BigDecimal totalQuantity;

    @ApiModelProperty(value = "Total gross quantity", position = 12)
    private BigDecimal totalGrossQuantity;

    @ApiModelProperty(value = "Fulfilled quantity", position = 13)
    private BigDecimal fulfilledQuantity;

    @ApiModelProperty(value = "Available quantity", position = 14)
    private BigDecimal availableQuantity;

    @ApiModelProperty(value = "Is stock available", position = 15)
    public Boolean isAvailable;

    @ApiModelProperty(value = "Production date", position = 16)
    @JsonSerialize(converter = SimpleDateConverter.Serialize.class)
    @JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    private Instant productionDate;

//    @ApiModelProperty(value = "Expiry date", position = 16)
//    public Instant expiryDate;

//    @ApiModelProperty(value = "Estimated delivery date", position = 17)
//    public Instant estimatedDeliveryDate;

    @ApiModelProperty(value = "Delivery time", position = 18)
    private Instant deliveryTime;

//    @ApiModelProperty(value = "Order ID", position = 19)
//    public Long orderId;

//    @ApiModelProperty(value = "Global order ID", position = 20)
//    public Long globalOrderId;

    @ApiModelProperty(value = "The produrct order that triggered creation of this stock order")
    private ApiProductOrder productOrder;

    @ApiModelProperty(value = "The processing order that created this stock order")
    private ApiProcessingOrder processingOrder;

    @ApiModelProperty(value = "Price per unit", position = 21)
    private BigDecimal pricePerUnit;

//    @ApiModelProperty(value = "Sales price per unit", position = 22)
//    public Float salesPricePerUnit;

    @ApiModelProperty(value = "Currency", position = 23)
    private String currency;

//    @ApiModelProperty(value = "Sales currency", position = 24)
//    public String salesCurrency;

    @ApiModelProperty(value = "Is order of type PURCHASE_ORDER", position = 25)
    private Boolean isPurchaseOrder;

    @ApiModelProperty(value = "Order type", position = 26)
    private OrderType orderType;

    @ApiModelProperty(value = "Internal LOT number", position = 27)
    private String internalLotNumber;

    @ApiModelProperty(value = "Comments", position = 31)
    private String comments;

    @ApiModelProperty(value = "Is women share", position = 33)
    private Boolean womenShare;

    @ApiModelProperty(value = "Cost", position = 34)
    private BigDecimal cost;

    @ApiModelProperty(value = "Paid", position = 35)
    private BigDecimal paid;

    @ApiModelProperty(value = "Balance", position = 36)
    private BigDecimal balance;

    // Relevant only for order type: STOCK_ORDER
//    @ApiModelProperty(value = "Input transactions for stock order. Read only.")
//    public List<ApiTransaction> inputTransactions;

//    @ApiModelProperty(value = "Client company", position = 38)
//    public ApiCompany client;

//    @ApiModelProperty(value = "Processing action", position = 40)
//    public ApiProcessingAction processingAction;

    @ApiModelProperty(value = "Preferred way of payment", position = 41)
    private PreferredWayOfPayment preferredWayOfPayment;

    @ApiModelProperty(value = "SAC number", position = 42)
    private Integer sacNumber;

    @ApiModelProperty(value = "Is order open", position = 43)
    private Boolean isOpenOrder;

    @ApiModelProperty(value = "Quote facility")
    private ApiFacility quoteFacility;

    @ApiModelProperty(value = "Quote company")
    private ApiCompany quoteCompany;

    @ApiModelProperty(value = "The company customer for whom the stock order is created")
    private ApiCompanyCustomer consumerCompanyCustomer;

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

    @ApiModelProperty(value = "Organic")
    private Boolean organic;

    @ApiModelProperty(value = "Tare")
    private BigDecimal tare;

    @ApiModelProperty(value = "Damaged price deduction")
    private BigDecimal damagedPriceDeduction;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
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

    public List<ApiActivityProof> getActivityProofs() {
        if (activityProofs == null) {
            activityProofs = new ArrayList<>();
        }
        return activityProofs;
    }

    public void setActivityProofs(List<ApiActivityProof> activityProofs) {
        this.activityProofs = activityProofs;
    }

    public List<ApiStockOrderEvidenceFieldValue> getRequiredEvidenceFieldValues() {
        if (requiredEvidenceFieldValues == null) {
            requiredEvidenceFieldValues = new ArrayList<>();
        }
        return requiredEvidenceFieldValues;
    }

    public void setRequiredEvidenceFieldValues(List<ApiStockOrderEvidenceFieldValue> requiredEvidenceFieldValues) {
        this.requiredEvidenceFieldValues = requiredEvidenceFieldValues;
    }

    public List<ApiStockOrderEvidenceTypeValue> getRequiredEvidenceTypeValues() {
        if (requiredEvidenceTypeValues == null) {
            requiredEvidenceTypeValues = new ArrayList<>();
        }
        return requiredEvidenceTypeValues;
    }

    public void setRequiredEvidenceTypeValues(List<ApiStockOrderEvidenceTypeValue> requiredEvidenceTypeValues) {
        this.requiredEvidenceTypeValues = requiredEvidenceTypeValues;
    }

    public List<ApiStockOrderEvidenceTypeValue> getOtherEvidenceDocuments() {
        if (otherEvidenceDocuments == null) {
            otherEvidenceDocuments = new ArrayList<>();
        }
        return otherEvidenceDocuments;
    }

    public void setOtherEvidenceDocuments(List<ApiStockOrderEvidenceTypeValue> otherEvidenceDocuments) {
        this.otherEvidenceDocuments = otherEvidenceDocuments;
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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalGrossQuantity() {
        return totalGrossQuantity;
    }

    public void setTotalGrossQuantity(BigDecimal totalGrossQuantity) {
        this.totalGrossQuantity = totalGrossQuantity;
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

    public ApiProductOrder getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ApiProductOrder productOrder) {
        this.productOrder = productOrder;
    }

    public ApiProcessingOrder getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(ApiProcessingOrder processingOrder) {
        this.processingOrder = processingOrder;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Integer getSacNumber() {
        return sacNumber;
    }

    public void setSacNumber(Integer sacNumber) {
        this.sacNumber = sacNumber;
    }

    public Boolean getOpenOrder() {
        return isOpenOrder;
    }

    public void setOpenOrder(Boolean openOrder) {
        isOpenOrder = openOrder;
    }

    public ApiFacility getQuoteFacility() {
        return quoteFacility;
    }

    public void setQuoteFacility(ApiFacility quoteFacility) {
        this.quoteFacility = quoteFacility;
    }

    public ApiCompany getQuoteCompany() {
        return quoteCompany;
    }

    public void setQuoteCompany(ApiCompany quoteCompany) {
        this.quoteCompany = quoteCompany;
    }

    public ApiCompanyCustomer getConsumerCompanyCustomer() {
        return consumerCompanyCustomer;
    }

    public void setConsumerCompanyCustomer(ApiCompanyCustomer consumerCompanyCustomer) {
        this.consumerCompanyCustomer = consumerCompanyCustomer;
    }

    public Boolean getOrganic() {
        return organic;
    }

    public void setOrganic(Boolean organic) {
        this.organic = organic;
    }

    public BigDecimal getTare() {
        return tare;
    }

    public void setTare(BigDecimal tare) {
        this.tare = tare;
    }

    public BigDecimal getDamagedPriceDeduction() {
        return damagedPriceDeduction;
    }

    public void setDamagedPriceDeduction(BigDecimal damagedPriceDeduction) {
        this.damagedPriceDeduction = damagedPriceDeduction;
    }

}
