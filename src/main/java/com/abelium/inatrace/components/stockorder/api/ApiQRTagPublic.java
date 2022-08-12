package com.abelium.inatrace.components.stockorder.api;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * API model for QR code tag history (public Stock order data) used in B2C page.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiQRTagPublic {

	@ApiModelProperty(value = "The QR code tag")
	private String qrTag;

	@ApiModelProperty(value = "The global (product) order of the Stock order")
	private String orderId;

	@ApiModelProperty(value = "The Producer name")
	private String producerName;

	@ApiModelProperty(value = "Price paid to producer in EUR/kg")
	private BigDecimal priceToProducer;

	@ApiModelProperty(value = "Price paid to farmers in EUR/kg")
	private BigDecimal priceToFarmer;

	@ApiModelProperty(value = "The cupping score entered during one of the processing actions")
	private BigDecimal cuppingScore;

	@ApiModelProperty(value = "The cupping flavour entered during one of the processing actions")
	private String cuppingFlavour;

	@ApiModelProperty(value = "The roasting profile entered during one of the processing actions")
	private String roastingProfile;

	private ApiHistoryTimeline historyTimeline;

	public String getQrTag() {
		return qrTag;
	}

	public void setQrTag(String qrTag) {
		this.qrTag = qrTag;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public BigDecimal getPriceToProducer() {
		return priceToProducer;
	}

	public void setPriceToProducer(BigDecimal priceToProducer) {
		this.priceToProducer = priceToProducer;
	}

	public BigDecimal getPriceToFarmer() {
		return priceToFarmer;
	}

	public void setPriceToFarmer(BigDecimal priceToFarmer) {
		this.priceToFarmer = priceToFarmer;
	}

	public BigDecimal getCuppingScore() {
		return cuppingScore;
	}

	public void setCuppingScore(BigDecimal cuppingScore) {
		this.cuppingScore = cuppingScore;
	}

	public String getCuppingFlavour() {
		return cuppingFlavour;
	}

	public void setCuppingFlavour(String cuppingFlavour) {
		this.cuppingFlavour = cuppingFlavour;
	}

	public String getRoastingProfile() {
		return roastingProfile;
	}

	public void setRoastingProfile(String roastingProfile) {
		this.roastingProfile = roastingProfile;
	}

	public ApiHistoryTimeline getHistoryTimeline() {
		return historyTimeline;
	}

	public void setHistoryTimeline(ApiHistoryTimeline historyTimeline) {
		this.historyTimeline = historyTimeline;
	}

}
