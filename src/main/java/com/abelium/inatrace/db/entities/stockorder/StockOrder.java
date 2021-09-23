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
	private Integer fullfilledQuantity;
	
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
	
	
}
