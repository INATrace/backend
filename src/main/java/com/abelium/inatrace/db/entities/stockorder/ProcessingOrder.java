package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ProcessingOrder extends TimestampEntity {
	
	@Version
	private Long entityVersion;
	
	@ManyToOne
	private ProcessingAction processingAction;
	
	@Column
	private Long initiatorUserId;
	
	@OneToMany // Verify relationship
	private List<StockOrder> targetStockOrders = new ArrayList<>();

	@Column
	private Float desiredQuantity;

	@ManyToOne
	private MeasureUnitType desiredQuantityUnit;

	@ManyToOne
	private Facility facility;
	
	@OneToMany // Verify relationship
	private List<Transaction> inputTransactions = new ArrayList<>();
	
	@OneToMany // Verify relationship
	private List<StockOrder> inputOrders = new ArrayList<>();

	public ProcessingAction getProcessingAction() {
		return processingAction;
	}

	public void setProcessingAction(ProcessingAction processingAction) {
		this.processingAction = processingAction;
	}

	public Long getInitiatorUserId() {
		return initiatorUserId;
	}

	public void setInitiatorUserId(Long initiatorUserId) {
		this.initiatorUserId = initiatorUserId;
	}

	public List<StockOrder> getTargetStockOrders() {
		return targetStockOrders;
	}

	public void setTargetStockOrders(List<StockOrder> targetStockOrders) {
		this.targetStockOrders = targetStockOrders;
	}

	public Float getDesiredQuantity() {
		return desiredQuantity;
	}

	public void setDesiredQuantity(Float desiredQuantity) {
		this.desiredQuantity = desiredQuantity;
	}

	public MeasureUnitType getDesiredQuantityUnit() {
		return desiredQuantityUnit;
	}

	public void setDesiredQuantityUnit(MeasureUnitType desiredQuantityUnit) {
		this.desiredQuantityUnit = desiredQuantityUnit;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public List<Transaction> getInputTransactions() {
		return inputTransactions;
	}

	public void setInputTransactions(List<Transaction> inputTransactions) {
		this.inputTransactions = inputTransactions;
	}

	public List<StockOrder> getInputOrders() {
		return inputOrders;
	}

	public void setInputOrders(List<StockOrder> inputOrders) {
		this.inputOrders = inputOrders;
	}
	
}
