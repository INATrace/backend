package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ActionType;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.payment.BulkPayment;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "StockOrder.getPurchaseOrderById",
				query = "SELECT so FROM StockOrder so "
						+ "WHERE so.id = :stockOrderId "
						+ "AND so.orderType = 'PURCHASE_ORDER'")
})
public class StockOrder extends TimestampEntity {

	@Version
	private Long entityVersion;

	@ManyToOne(optional = false)
	private User createdBy;

	@ManyToOne
	private User updatedBy;

	@Column
	private String identifier;

	// Cooperative employee
	@Column
	private Long creatorId;

	// Farmer representative - collector
	@ManyToOne
	private UserCustomer representativeOfProducerUserCustomer;

	// Farmer
	@ManyToOne
	private UserCustomer producerUserCustomer;
	
	@OneToOne(cascade = CascadeType.ALL)
	private StockOrderLocation productionLocation;
	
	@OneToOne
	private CompanyCustomer consumerCompanyCustomer; // probably not used for purchase
	
	@ManyToOne
	private SemiProduct semiProduct;
	
	@ManyToOne
	private Facility facility;
	
	@ManyToOne
	private ProcessingAction processingActionDef;
	
	@ManyToOne
	private BulkPayment bulkPayment;

	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Certification> certifications; // probably not used for purchase

	// The required processing evidence fields values - the available values are sourced from the
	// selected Processing action definition;
	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockOrderPEFieldValue> processingEFValues;

	// The required processing evidence documents - the available values are sourced from the
	// selected Processing action definition;
	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockOrderPETypeValue> documentRequirements;

	// Activity proofs that were provided while creating or updating a purchase order
	@OneToMany(mappedBy = "stockOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockOrderActivityProof> activityProofs;

	// A stock (purchase) order can be divided in many payments
	@OneToMany(mappedBy = "payingCompany", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Payment> payments = new ArrayList<>();

	@ManyToOne
	private Company company;
	
	@ManyToOne
	private MeasureUnitType measurementUnitType;
	
	@Column
	@NotNull
	private Integer totalQuantity;
	
	@Column
	@NotNull
	private Integer fulfilledQuantity;
	
	@Column
	@NotNull
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
	
	@Column
	private BigDecimal pricePerUnit;
	
	@Column
	private BigDecimal salesPricePerUnit;
	
	@Column
	private String currency;
	
	@Column
	private String salesCurrency;
	
	@Column
	private Boolean isPurchaseOrder;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private OrderType orderType;
	
	@OneToOne
	private GradeAbbreviationType gradeAbbreviation;
	
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
	private Boolean womenShare;
	
	// CALCULATED section

	@Column
	private BigDecimal cost;
	
	@Column
	private BigDecimal paid;

	@Column
	private BigDecimal balance;

	@Column
	private String lotLabel;
	
	@Column
    private Instant startOfDrying;
	
	@ManyToOne
	private Company client;
	
	@Column
	private String flavourProfile;
	
	@OneToOne // Verify relationship
	private ProcessingOrder processingOrder;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private PreferredWayOfPayment preferredWayOfPayment;
	
	@Column
	private Integer sacNumber;
	
//	TODO: one to many self referencing?
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
    private BigDecimal pricePerUnitForOwner;
	
	@Column
    private BigDecimal pricePerUnitForBuyer;
	
	@Column
    private BigDecimal exchangeRateAtBuyer;
	
	@Column
    private BigDecimal pricePerUnitForEndCustomer;
	
	@Column
    private BigDecimal exchangeRateAtEndCustomer;
	
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
	
	@OneToOne
    private StockOrderLocation locationOfEndDelivery;
	
	@Column
    private Instant dateOfEndDelivery;
	
	@Column
    private Boolean requiredWomensCoffee;
	
	@ManyToOne // verify relationship
    private GradeAbbreviationType requiredQuality;
	
	@Column
    private Instant shippedAtDateFromOriginPort;
	
	@Column
    private Instant arrivedAtDateToDestinationPort;

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public UserCustomer getRepresentativeOfProducerUserCustomer() {
		return representativeOfProducerUserCustomer;
	}

	public void setRepresentativeOfProducerUserCustomer(UserCustomer representativeOfProducerCustomer) {
		this.representativeOfProducerUserCustomer = representativeOfProducerCustomer;
	}

	public UserCustomer getProducerUserCustomer() {
		return producerUserCustomer;
	}

	public void setProducerUserCustomer(UserCustomer producerUserCustomer) {
		this.producerUserCustomer = producerUserCustomer;
	}

	public List<Certification> getCertifications() {
		if (certifications == null)
			certifications = new ArrayList<>();
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public CompanyCustomer getConsumerCompanyCustomer() {
		return consumerCompanyCustomer;
	}

	public void setConsumerCompanyCustomer(CompanyCustomer consumerCompanyCustomer) {
		this.consumerCompanyCustomer = consumerCompanyCustomer;
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

	public List<StockOrderPEFieldValue> getProcessingEFValues() {
		if (processingEFValues == null) {
			processingEFValues = new ArrayList<>();
		}
		return processingEFValues;
	}

	public List<StockOrderPETypeValue> getDocumentRequirements() {
		if (documentRequirements == null) {
			documentRequirements = new ArrayList<>();
		}
		return documentRequirements;
	}

	public List<StockOrderActivityProof> getActivityProofs() {
		if (activityProofs == null) {
			activityProofs = new ArrayList<>();
		}
		return activityProofs;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getSalesPricePerUnit() {
		return salesPricePerUnit;
	}

	public void setSalesPricePerUnit(BigDecimal salesPricePerUnit) {
		this.salesPricePerUnit = salesPricePerUnit;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
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

	public GradeAbbreviationType getGradeAbbreviation() {
		return gradeAbbreviation;
	}

	public void setGradeAbbreviation(GradeAbbreviationType gradeAbbreviation) {
		this.gradeAbbreviation = gradeAbbreviation;
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

	public ProcessingAction getProcessingActionDef() {
		return processingActionDef;
	}

	public void setProcessingActionDef(ProcessingAction processingAction) {
		this.processingActionDef = processingAction;
	}
	
	public BulkPayment getBulkPayment() {
		return bulkPayment;
	}

	public void setBulkPayment(BulkPayment bulkPayment) {
		this.bulkPayment = bulkPayment;
	}

	public ProcessingOrder getProcessingOrder() {
		return processingOrder;
	}

	public void setProcessingOrder(ProcessingOrder processingOrder) {
		this.processingOrder = processingOrder;
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

	public BigDecimal getPricePerUnitForOwner() {
		return pricePerUnitForOwner;
	}

	public void setPricePerUnitForOwner(BigDecimal pricePerUnitForOwner) {
		this.pricePerUnitForOwner = pricePerUnitForOwner;
	}

	public BigDecimal getPricePerUnitForBuyer() {
		return pricePerUnitForBuyer;
	}

	public void setPricePerUnitForBuyer(BigDecimal pricePerUnitForBuyer) {
		this.pricePerUnitForBuyer = pricePerUnitForBuyer;
	}

	public BigDecimal getExchangeRateAtBuyer() {
		return exchangeRateAtBuyer;
	}

	public void setExchangeRateAtBuyer(BigDecimal exchangeRateAtBuyer) {
		this.exchangeRateAtBuyer = exchangeRateAtBuyer;
	}

	public BigDecimal getPricePerUnitForEndCustomer() {
		return pricePerUnitForEndCustomer;
	}

	public void setPricePerUnitForEndCustomer(BigDecimal pricePerUnitForEndCustomer) {
		this.pricePerUnitForEndCustomer = pricePerUnitForEndCustomer;
	}

	public BigDecimal getExchangeRateAtEndCustomer() {
		return exchangeRateAtEndCustomer;
	}

	public void setExchangeRateAtEndCustomer(BigDecimal exchangeRateAtEndCustomer) {
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

	public StockOrderLocation getProductionLocation() {
		return productionLocation;
	}

	public void setProductionLocation(StockOrderLocation productionLocation) {
		this.productionLocation = productionLocation;
	}

	public StockOrderLocation getLocationOfEndDelivery() {
		return locationOfEndDelivery;
	}

	public void setLocationOfEndDelivery(StockOrderLocation locationOfEndDelivery) {
		this.locationOfEndDelivery = locationOfEndDelivery;
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

	public GradeAbbreviationType getRequiredQuality() {
		return requiredQuality;
	}

	public void setRequiredQuality(GradeAbbreviationType requiredQuality) {
		this.requiredQuality = requiredQuality;
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
