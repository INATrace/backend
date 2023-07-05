package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.components.productorder.api.ApiProductOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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

    @ApiModelProperty(value = "Option to determine price later after taking delivery")
    private Boolean priceDeterminedLater;

    @ApiModelProperty(value = "Final product")
    private ApiFinalProduct finalProduct;

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

    @ApiModelProperty(value = "Total quantity of this stock order is not within the expected range")
    public Boolean outQuantityNotInRange;

    @ApiModelProperty(value = "Production date", position = 16)
    private LocalDate productionDate;

    @ApiModelProperty(value = "Delivery time", position = 18)
    private LocalDate deliveryTime;

    @ApiModelProperty(value = "The produrct order that triggered creation of this stock order")
    private ApiProductOrder productOrder;

    @ApiModelProperty(value = "User entered Order ID when placing Quote order")
    private String orderId;

    @ApiModelProperty(value = "The processing order that created this stock order")
    private ApiProcessingOrder processingOrder;

    @ApiModelProperty(value = "Price per unit", position = 21)
    private BigDecimal pricePerUnit;

    @ApiModelProperty(value = "Currency", position = 23)
    private String currency;

    @ApiModelProperty(value = "Is order of type PURCHASE_ORDER", position = 25)
    private Boolean isPurchaseOrder;

    @ApiModelProperty(value = "Order type", position = 26)
    private OrderType orderType;

    @ApiModelProperty(value = "The prefix for the LOT name - retrieved from the Processing action")
    private String lotPrefix;

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

    @ApiModelProperty(value = "Price per unit for end customer", position = 49)
    private BigDecimal pricePerUnitForEndCustomer;

    @ApiModelProperty(value = "Currency for price per unit for end customer")
    private String currencyForEndCustomer;

    @ApiModelProperty(value = "Organic")
    private Boolean organic;

    @ApiModelProperty(value = "Tare")
    private BigDecimal tare;

    @ApiModelProperty(value = "Damaged price deduction")
    private BigDecimal damagedPriceDeduction;

    @ApiModelProperty(value = "Damaged weight deduction")
    private BigDecimal damagedWeightDeduction;

    @ApiModelProperty(value = "Generated UUID tag for this stock order QR code")
    private String qrCodeTag;

    @ApiModelProperty(value = "The final product for which the QR code tag is generated")
    private ApiFinalProduct qrCodeTagFinalProduct;

    @ApiModelProperty(value = "Payments for stock order")
    private List<ApiPayment> payments;

    @ApiModelProperty(value = "The ID from which this repacked stock order was created; This ID is generated and provided by the client; Only applicable for repacked stock orders")
    private String repackedOriginStockOrderId;

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

    public Boolean getPriceDeterminedLater() {
        return priceDeterminedLater;
    }

    public void setPriceDeterminedLater(Boolean priceDeterminedLater) {
        this.priceDeterminedLater = priceDeterminedLater;
    }

    public ApiFinalProduct getFinalProduct() {
        return finalProduct;
    }

    public void setFinalProduct(ApiFinalProduct finalProduct) {
        this.finalProduct = finalProduct;
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

    public Boolean getOutQuantityNotInRange() {
        return outQuantityNotInRange;
    }

    public void setOutQuantityNotInRange(Boolean outQuantityNotInRange) {
        this.outQuantityNotInRange = outQuantityNotInRange;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public ApiProductOrder getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ApiProductOrder productOrder) {
        this.productOrder = productOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getLotPrefix() {
        return lotPrefix;
    }

    public void setLotPrefix(String lotPrefix) {
        this.lotPrefix = lotPrefix;
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

    public BigDecimal getPricePerUnitForEndCustomer() {
        return pricePerUnitForEndCustomer;
    }

    public void setPricePerUnitForEndCustomer(BigDecimal pricePerUnitForEndCustomer) {
        this.pricePerUnitForEndCustomer = pricePerUnitForEndCustomer;
    }

    public String getCurrencyForEndCustomer() {
        return currencyForEndCustomer;
    }

    public void setCurrencyForEndCustomer(String currencyForEndCustomer) {
        this.currencyForEndCustomer = currencyForEndCustomer;
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

    public BigDecimal getDamagedWeightDeduction() {
        return damagedWeightDeduction;
    }

    public void setDamagedWeightDeduction(BigDecimal damagedWeightDeduction) {
        this.damagedWeightDeduction = damagedWeightDeduction;
    }

    public String getQrCodeTag() {
        return qrCodeTag;
    }

    public void setQrCodeTag(String qrCodeTag) {
        this.qrCodeTag = qrCodeTag;
    }

    public ApiFinalProduct getQrCodeTagFinalProduct() {
        return qrCodeTagFinalProduct;
    }

    public void setQrCodeTagFinalProduct(ApiFinalProduct qrCodeTagFinalProduct) {
        this.qrCodeTagFinalProduct = qrCodeTagFinalProduct;
    }

    public List<ApiPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<ApiPayment> payments) {
        this.payments = payments;
    }

    public String getRepackedOriginStockOrderId() {
        return repackedOriginStockOrderId;
    }

    public void setRepackedOriginStockOrderId(String repackedOriginStockOrderId) {
        this.repackedOriginStockOrderId = repackedOriginStockOrderId;
    }
}
