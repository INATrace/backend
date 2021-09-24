package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.components.codebook.action_type.api.ApiActionType;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.product.api.ApiLocation;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.stockorder.OrderType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@Validated
public class ApiStockOrder {

    @ApiModelProperty(value = "Timestamp indicates when stock order have been updated", position = 1)
    public Instant updateTimestamp;

    @ApiModelProperty(value = "ID of user who created stock order", position = 2)
    public Long creatorId;

    @ApiModelProperty(value = "Representative of producer customer", position = 3)
    public ApiUserCustomer representativeOfProducerCustomer;

    @ApiModelProperty(value = "Producer user customer", position = 4)
    public ApiUserCustomer producerUserCustomer;

    @ApiModelProperty(value = "Production location", position = 5)
    public ApiLocation productionLocation;

    @ApiModelProperty(value = "Certification", position = 6)
    @Valid
    public List<ApiCertification> certifications;

    @ApiModelProperty(value = "Semi product", position = 7)
    public ApiSemiProduct apiSemiProduct;

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
    public Instant productionDate;

    @ApiModelProperty(value = "Expiry date", position = 16)
    public Instant expiryDate;

    @ApiModelProperty(value = "Estimated delivery date", position = 17)
    public Instant estimatedDeliveryDate;

    @ApiModelProperty(value = "Delivery time", position = 18)
    public Instant deliveryTime;

    @ApiModelProperty(value = "Order ID", position = 19)
    public Long orderId;

    @ApiModelProperty(value = "Global order ID", position = 20)
    public Long globalOrderId;

    @ApiModelProperty(value = "Price per unit", position = 21)
    public Float pricePerUnit;

    @ApiModelProperty(value = "Sales price per unit", position = 22)
    public Float salesPricePerUnit;

    @ApiModelProperty(value = "Currency", position = 23)
    public String currency;

    @ApiModelProperty(value = "Sales currency", position = 24)
    public String salesCurrency;

    @ApiModelProperty(value = "?", position = 25)
    public Boolean isPurchaseOrder;

    @ApiModelProperty(value = "Order type", position = 26)
    public OrderType orderType;

    @ApiModelProperty(value = "Order type", position = 27)
    public String internalLotNumber;

    @ApiModelProperty(value = "LOT number", position = 28)
    public String lotNumber;

    @ApiModelProperty(value = "LOT label", position = 29)
    public String lotLabel;

    @ApiModelProperty(value = "Screen size", position = 30)
    public String screenSize;

    // TODO: Why are comments string?
    @ApiModelProperty(value = "Comments", position = 31)
    public String comments;

    @ApiModelProperty(value = "Action type", position = 32)
    public ApiActionType actionType;

    @ApiModelProperty(value = "Is women share", position = 33)
    public Boolean isWomenShare;

    @ApiModelProperty(value = "Cost", position = 34)
    public Float cost;

    @ApiModelProperty(value = "Paid", position = 35)
    public Float paid;

    @ApiModelProperty(value = "Balance", position = 36)
    public Float balance;

    @ApiModelProperty(value = "Date when product has been started drying", position = 37)
    public Instant startOfDrying;

    @ApiModelProperty(value = "Client company", position = 38)
    public ApiCompany client;

    @ApiModelProperty(value = "Flavour profile", position = 39)
    public String flavourProfile;

    @ApiModelProperty(value = "Processing action", position = 40)
    public ApiProcessingAction processingAction;

    @ApiModelProperty(value = "Preferred way of payment", position = 41)
    private OrderType preferredWayOfPayment;

    @ApiModelProperty(value = "SAC number", position = 42)
    private Integer sacNumber;

    @ApiModelProperty(value = "Is order open", position = 43)
    private Boolean isOpenOrder;

    @ApiModelProperty(value = "Quote facility", position = 44)
    private Facility quoteFacility;

    @ApiModelProperty(value = "Quote Company", position = 45)
    private Company quoteCompany;

    @ApiModelProperty(value = "Price per unit for owner", position = 46)
    private Float pricePerUnitForOwner;

    @ApiModelProperty(value = "Price per unit for buyer", position = 47)
    private Float pricePerUnitForBuyer;

    @ApiModelProperty(value = "Exchange rate at buyer", position = 48)
    private Float exchangeRateAtBuyer;

    @ApiModelProperty(value = "Price per unit for end customer", position = 49)
    private Float pricePerUnitForEndCustomer;

    @ApiModelProperty(value = "Exchange rate at end customer", position = 50)
    private Float exchangeRateAtEndCustomer;

    @ApiModelProperty(value = "Cupping result", position = 51)
    private String cuppingResult;

    @ApiModelProperty(value = "Cupping grade", position = 52)
    private String cuppingGrade;

    @ApiModelProperty(value = "Cupping flavour", position = 53)
    private String cuppingFlavour;

    @ApiModelProperty(value = "Roasting date", position = 54)
    private Instant roastingDate;

    @ApiModelProperty(value = "Roasting profile", position = 55)
    private String roastingProfile;

    @ApiModelProperty(value = "Shipper details", position = 56)
    private String shipperDetails;

    @ApiModelProperty(value = "Carrier details", position = 57)
    private String carrierDetails;

    @ApiModelProperty(value = "Part of loading", position = 58)
    private String portOfLoading;

    @ApiModelProperty(value = "Port od discharge", position = 59)
    private String portOfDischarge;

    @ApiModelProperty(value = "Date of end delivery", position = 60)
    private Instant dateOfEndDelivery;

    @ApiModelProperty(value = "Required women's coffee", position = 61)
    private Boolean requiredWomensCoffee;

    @ApiModelProperty(value = "Shipper at date from origin port", position = 62)
    private Instant shippedAtDateFromOriginPort;

    @ApiModelProperty(value = "Arrived at date to destination port", position = 63)
    private Instant arrivedAtDateToDestinationPort;

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public ApiUserCustomer getRepresentativeOfProducerCustomer() {
        return representativeOfProducerCustomer;
    }

    public void setRepresentativeOfProducerCustomer(ApiUserCustomer representativeOfProducerCustomer) {
        this.representativeOfProducerCustomer = representativeOfProducerCustomer;
    }

    public ApiUserCustomer getProducerUserCustomer() {
        return producerUserCustomer;
    }

    public void setProducerUserCustomer(ApiUserCustomer producerUserCustomer) {
        this.producerUserCustomer = producerUserCustomer;
    }

    public ApiLocation getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(ApiLocation productionLocation) {
        this.productionLocation = productionLocation;
    }

    public List<ApiCertification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<ApiCertification> certifications) {
        this.certifications = certifications;
    }

    public ApiSemiProduct getApiSemiProduct() {
        return apiSemiProduct;
    }

    public void setApiSemiProduct(ApiSemiProduct apiSemiProduct) {
        this.apiSemiProduct = apiSemiProduct;
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

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Instant getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Instant estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Instant deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGlobalOrderId() {
        return globalOrderId;
    }

    public void setGlobalOrderId(Long globalOrderId) {
        this.globalOrderId = globalOrderId;
    }

    public Float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Float getSalesPricePerUnit() {
        return salesPricePerUnit;
    }

    public void setSalesPricePerUnit(Float salesPricePerUnit) {
        this.salesPricePerUnit = salesPricePerUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSalesCurrency() {
        return salesCurrency;
    }

    public void setSalesCurrency(String salesCurrency) {
        this.salesCurrency = salesCurrency;
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLotLabel() {
        return lotLabel;
    }

    public void setLotLabel(String lotLabel) {
        this.lotLabel = lotLabel;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ApiActionType getActionType() {
        return actionType;
    }

    public void setActionType(ApiActionType actionType) {
        this.actionType = actionType;
    }

    public Boolean getWomenShare() {
        return isWomenShare;
    }

    public void setWomenShare(Boolean womenShare) {
        isWomenShare = womenShare;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Instant getStartOfDrying() {
        return startOfDrying;
    }

    public void setStartOfDrying(Instant startOfDrying) {
        this.startOfDrying = startOfDrying;
    }

    public ApiCompany getClient() {
        return client;
    }

    public void setClient(ApiCompany client) {
        this.client = client;
    }

    public String getFlavourProfile() {
        return flavourProfile;
    }

    public void setFlavourProfile(String flavourProfile) {
        this.flavourProfile = flavourProfile;
    }

    public ApiProcessingAction getProcessingAction() {
        return processingAction;
    }

    public void setProcessingAction(ApiProcessingAction processingAction) {
        this.processingAction = processingAction;
    }

    public OrderType getPreferredWayOfPayment() {
        return preferredWayOfPayment;
    }

    public void setPreferredWayOfPayment(OrderType preferredWayOfPayment) {
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

    public Facility getQuoteFacility() {
        return quoteFacility;
    }

    public void setQuoteFacility(Facility quoteFacility) {
        this.quoteFacility = quoteFacility;
    }

    public Company getQuoteCompany() {
        return quoteCompany;
    }

    public void setQuoteCompany(Company quoteCompany) {
        this.quoteCompany = quoteCompany;
    }

    public Float getPricePerUnitForOwner() {
        return pricePerUnitForOwner;
    }

    public void setPricePerUnitForOwner(Float pricePerUnitForOwner) {
        this.pricePerUnitForOwner = pricePerUnitForOwner;
    }

    public Float getPricePerUnitForBuyer() {
        return pricePerUnitForBuyer;
    }

    public void setPricePerUnitForBuyer(Float pricePerUnitForBuyer) {
        this.pricePerUnitForBuyer = pricePerUnitForBuyer;
    }

    public Float getExchangeRateAtBuyer() {
        return exchangeRateAtBuyer;
    }

    public void setExchangeRateAtBuyer(Float exchangeRateAtBuyer) {
        this.exchangeRateAtBuyer = exchangeRateAtBuyer;
    }

    public Float getPricePerUnitForEndCustomer() {
        return pricePerUnitForEndCustomer;
    }

    public void setPricePerUnitForEndCustomer(Float pricePerUnitForEndCustomer) {
        this.pricePerUnitForEndCustomer = pricePerUnitForEndCustomer;
    }

    public Float getExchangeRateAtEndCustomer() {
        return exchangeRateAtEndCustomer;
    }

    public void setExchangeRateAtEndCustomer(Float exchangeRateAtEndCustomer) {
        this.exchangeRateAtEndCustomer = exchangeRateAtEndCustomer;
    }

    public String getCuppingResult() {
        return cuppingResult;
    }

    public void setCuppingResult(String cuppingResult) {
        this.cuppingResult = cuppingResult;
    }

    public String getCuppingGrade() {
        return cuppingGrade;
    }

    public void setCuppingGrade(String cuppingGrade) {
        this.cuppingGrade = cuppingGrade;
    }

    public String getCuppingFlavour() {
        return cuppingFlavour;
    }

    public void setCuppingFlavour(String cuppingFlavour) {
        this.cuppingFlavour = cuppingFlavour;
    }

    public Instant getRoastingDate() {
        return roastingDate;
    }

    public void setRoastingDate(Instant roastingDate) {
        this.roastingDate = roastingDate;
    }

    public String getRoastingProfile() {
        return roastingProfile;
    }

    public void setRoastingProfile(String roastingProfile) {
        this.roastingProfile = roastingProfile;
    }

    public String getShipperDetails() {
        return shipperDetails;
    }

    public void setShipperDetails(String shipperDetails) {
        this.shipperDetails = shipperDetails;
    }

    public String getCarrierDetails() {
        return carrierDetails;
    }

    public void setCarrierDetails(String carrierDetails) {
        this.carrierDetails = carrierDetails;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public Instant getDateOfEndDelivery() {
        return dateOfEndDelivery;
    }

    public void setDateOfEndDelivery(Instant dateOfEndDelivery) {
        this.dateOfEndDelivery = dateOfEndDelivery;
    }

    public Boolean getRequiredWomensCoffee() {
        return requiredWomensCoffee;
    }

    public void setRequiredWomensCoffee(Boolean requiredWomensCoffee) {
        this.requiredWomensCoffee = requiredWomensCoffee;
    }

    public Instant getShippedAtDateFromOriginPort() {
        return shippedAtDateFromOriginPort;
    }

    public void setShippedAtDateFromOriginPort(Instant shippedAtDateFromOriginPort) {
        this.shippedAtDateFromOriginPort = shippedAtDateFromOriginPort;
    }

    public Instant getArrivedAtDateToDestinationPort() {
        return arrivedAtDateToDestinationPort;
    }

    public void setArrivedAtDateToDestinationPort(Instant arrivedAtDateToDestinationPort) {
        this.arrivedAtDateToDestinationPort = arrivedAtDateToDestinationPort;
    }
}
