package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.ActionType;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
public class Transaction extends TimestampEntity {
	
	@Version
	private Long entityVersion;
	
	@ManyToOne
	private Company company;

	@Column
	private Long initiationUserId;
	
	@OneToOne
	private StockOrder sourceStockOrder;

	@OneToOne
	private StockOrder targetStockOrder;

	@ManyToOne
	private SemiProduct semiProduct;
	
	@ManyToOne
	private Facility sourceFacility;

	@ManyToOne
	private Facility targetFacility;

	@Column
	private Boolean isProcessing;
	
	@OneToOne
	private ActionType actionType;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private TransactionStatus status;
	
	@Column
	private Long shipmentId;

	@OneToOne
	private MeasureUnitType inputMeasureUnitType;

	@OneToOne
	private MeasureUnitType outputMeasureUnitType;
	
	@Column
	private BigDecimal inputQuantity;

	@Column
	private BigDecimal outputQuantity;
	
	@Column
	private BigDecimal pricePerUnit;
	
	@Column
	private String currency;
	
	@OneToOne
	private GradeAbbreviationType gradeAbbreviation;
	
	@Column
	private String rejectComment;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getInitiationUserId() {
		return initiationUserId;
	}

	public void setInitiationUserId(Long initiationUserId) {
		this.initiationUserId = initiationUserId;
	}

	public StockOrder getSourceStockOrder() {
		return sourceStockOrder;
	}

	public void setSourceStockOrder(StockOrder sourceStockOrder) {
		this.sourceStockOrder = sourceStockOrder;
	}

	public StockOrder getTargetStockOrder() {
		return targetStockOrder;
	}

	public void setTargetStockOrder(StockOrder targetStockOrder) {
		this.targetStockOrder = targetStockOrder;
	}

	public SemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(SemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public Facility getSourceFacility() {
		return sourceFacility;
	}

	public void setSourceFacility(Facility sourceFacility) {
		this.sourceFacility = sourceFacility;
	}

	public Facility getTargetFacility() {
		return targetFacility;
	}

	public void setTargetFacility(Facility targetFacility) {
		this.targetFacility = targetFacility;
	}

	public Boolean getIsProcessing() {
		return isProcessing;
	}

	public void setIsProcessing(Boolean isProcessing) {
		this.isProcessing = isProcessing;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public MeasureUnitType getInputMeasureUnitType() {
		return inputMeasureUnitType;
	}

	public void setInputMeasureUnitType(MeasureUnitType inputMeasureUnitType) {
		this.inputMeasureUnitType = inputMeasureUnitType;
	}

	public MeasureUnitType getOutputMeasureUnitType() {
		return outputMeasureUnitType;
	}

	public void setOutputMeasureUnitType(MeasureUnitType outputMeasureUnitType) {
		this.outputMeasureUnitType = outputMeasureUnitType;
	}

	public BigDecimal getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(BigDecimal inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public BigDecimal getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(BigDecimal outputQuantity) {
		this.outputQuantity = outputQuantity;
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

	public GradeAbbreviationType getGradeAbbreviation() {
		return gradeAbbreviation;
	}

	public void setGradeAbbreviation(GradeAbbreviationType gradeAbbreviation) {
		this.gradeAbbreviation = gradeAbbreviation;
	}

	public String getRejectComment() {
		return rejectComment;
	}

	public void setRejectComment(String rejectComment) {
		this.rejectComment = rejectComment;
	}
	
}
