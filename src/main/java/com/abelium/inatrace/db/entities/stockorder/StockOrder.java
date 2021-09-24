package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ActionType;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.Location;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class StockOrder extends TimestampEntity {

	@Version
	private Long entityVersion;

	@Column
    private Instant updateTimestamp;
	
	@Column
	private Long creatorId; // logged user? 
	
	@Column
	private UserCustomer representativeOfProducerCustomer; // farmer?
	
	@Column
	private UserCustomer producerUserCustomer; // farmer?
	
	@Column
	private Location productionLocation;
	
	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Certification> certifications = new ArrayList<>();
	
//	TODO: define relationship
//	private CompanyCustomer consumerCompanyCustomer;
	
	@ManyToOne
	private SemiProduct semiProduct;
	
	@ManyToOne
	private Facility facility;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private MeasureUnitType measurementUnitType;
	
	@Column
	private Integer totalQuantity;
	
	@Column
	private Integer fulfilledQuantity;
	
	@Column
	private Integer availableQuantity;
	
	@Column
	private Boolean isAvailable;
	
	@Column
	private Instant productionDate;
	
	@Column
	private Instant expiryDate;
	
	@Column
	private Instant estimatedDeliveryDate;
	
	@Column
	private Instant deliveryTime;
	
	@Column
	private Long orderId;
	
	@Column
	private Long globalOrderId;
	
//	@OneToMany
//	private List<ProcessingEvidenceType> documentRequirements; // Check with Pece if this is correct, might be some other type of document

	@Column
	private Float pricePerUnit;
	
	@Column
	private Float salesPricePerUnit;
	
	@Column
	private String currency;
	
	@Column
	private String salesCurrency;
	
	@Column
	private Boolean isPurchaseOrder;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private OrderType orderType;
	
//	@Column
//	private GradeAbbreviationType gradeAbbreviation; // Seems to be an empty class?
	
	@Column
	private String internalLotNumber;

	@Column
	private String lotNumber;

	@Column
	private String screenSize;

	@Column
	private String comments;

	@OneToOne
	private ActionType actionType;
	
	@Column
	private Boolean isWomenShare;
	
	@Column
	private Float cost;
	
	@Column
	private Float paid;

	@Column
	private Float balance;
	
//	TODO: Create Transaction class
//	@OneToMany
//	private List<Transaction> inputTransactions = new ArrayList<>();
	
//	@OneToMany
//	private List<Transaction> outputTransactions = new ArrayList<>();

	@Column
	private String lotLabel;
	
	@Column
    private Instant startOfDrying;
	
	@Column 
	private Company client;
	
	@Column
	private String flavourProfile;
	
	@ManyToOne
	private ProcessingAction processingAction;
	
//	TODO class
//	private ProcessingOrder processingOrder; 
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private OrderType preferredWayOfPayment;
	
	@Column
	private Integer sacNumber;
	
//	TODO: one to many self referencing
//	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<StockOrder> triggerOrders = new ArrayList<>();
	
//	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<StockOrder> triggeredOrders = new ArrayList<>();
	
//	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<StockOrder> inputOrders = new ArrayList<>();
	
	@Column
	private Boolean isOpenOrder;
	
	@ManyToOne
	private Facility quoteFacility;
	
	@ManyToOne
	private Company quoteCompany;
	
	@Column
    private Float pricePerUnitForOwner;
	
	@Column
    private Float pricePerUnitForBuyer;
	
	@Column
    private Float exchangeRateAtBuyer;
	
	@Column
    private Float pricePerUnitForEndCustomer;
	
	@Column
    private Float exchangeRateAtEndCustomer;
	
	@Column
    private String cuppingResult;
	
	@Column
    private String cuppingGrade;
	
	@Column
    private String cuppingFlavour;
	
	@Column
    private Instant roastingDate;
	
	@Column
    private String roastingProfile;
	
	@Column
    private String shipperDetails;
	
	@Column
    private String carrierDetails;
	
	@Column
    private String portOfLoading;
	
	@Column
    private String portOfDischarge;
	
//	TODO: define relationship
//    private Location locationOfEndDelivery;
	
	@Column
    private Instant dateOfEndDelivery;
	
	@Column
    private Boolean requiredWomensCoffee;
	
//	TODO: define relationship
//    private GradeAbbreviationType requiredQuality;
	
	@Column
    private Instant shippedAtDateFromOriginPort;
	
	@Column
    private Instant arrivedAtDateToDestinationPort;

	@Override
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

	public UserCustomer getRepresentativeOfProducerCustomer() {
		return representativeOfProducerCustomer;
	}

	public void setRepresentativeOfProducerCustomer(UserCustomer representativeOfProducerCustomer) {
		this.representativeOfProducerCustomer = representativeOfProducerCustomer;
	}

	public UserCustomer getProducerUserCustomer() {
		return producerUserCustomer;
	}

	public void setProducerUserCustomer(UserCustomer producerUserCustomer) {
		this.producerUserCustomer = producerUserCustomer;
	}

	public Location getProductionLocation() {
		return productionLocation;
	}

	public void setProductionLocation(Location productionLocation) {
		this.productionLocation = productionLocation;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public SemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(SemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public MeasureUnitType getMeasurementUnitType() {
		return measurementUnitType;
	}

	public void setMeasurementUnitType(MeasureUnitType measurementUnitType) {
		this.measurementUnitType = measurementUnitType;
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

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
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

	public String getLotLabel() {
		return lotLabel;
	}

	public void setLotLabel(String lotLabel) {
		this.lotLabel = lotLabel;
	}

	public Instant getStartOfDrying() {
		return startOfDrying;
	}

	public void setStartOfDrying(Instant startOfDrying) {
		this.startOfDrying = startOfDrying;
	}

	public Company getClient() {
		return client;
	}

	public void setClient(Company client) {
		this.client = client;
	}

	public String getFlavourProfile() {
		return flavourProfile;
	}

	public void setFlavourProfile(String flavourProfile) {
		this.flavourProfile = flavourProfile;
	}

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
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
