package com.abelium.inatrace.components.payment.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.db.entities.payment.PaymentPurposeType;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.annotations.ApiModelProperty;

public class ApiPaymentCsv extends ApiBaseEntity {

	
	@ApiModelProperty(value = "Payment purpose type")
	private PaymentPurposeType paymentPurposeType;
	
	@ApiModelProperty(value = "Payment amount paid to the farmer")
	private BigDecimal amountPaidToTheFarmer;
	
	@ApiModelProperty(value = "Farmer name")
	private String farmerName;
	
	@ApiModelProperty(value = "Delivery time")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    private Instant deliveryTime;
	
	@ApiModelProperty(value = "Payment date")
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
    private Instant paymentDate;
	
	@ApiModelProperty(value = "Payment status")
	private PaymentStatus paymentStatus;
	
	@ApiModelProperty(value = "Preferred way of payment")
	private PreferredWayOfPayment preferredWayOfPayment;

	public PaymentPurposeType getPaymentPurposeType() {
		return paymentPurposeType;
	}

	public void setPaymentPurposeType(PaymentPurposeType paymentPurposeType) {
		this.paymentPurposeType = paymentPurposeType;
	}

	public BigDecimal getAmountPaidToTheFarmer() {
		return amountPaidToTheFarmer;
	}

	public void setAmountPaidToTheFarmer(BigDecimal amountPaidToTheFarmer) {
		this.amountPaidToTheFarmer = amountPaidToTheFarmer;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public Instant getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Instant deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Instant getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Instant paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PreferredWayOfPayment getPreferredWayOfPayment() {
		return preferredWayOfPayment;
	}

	public void setPreferredWayOfPayment(PreferredWayOfPayment preferredWayOfPayment) {
		this.preferredWayOfPayment = preferredWayOfPayment;
	}
	
}
