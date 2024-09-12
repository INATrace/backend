package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.components.common.api.ApiCertification;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * API model for QR code tag history (public Stock order data) used in B2C page.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiQRTagPublic {

	@Schema(description = "The QR code tag")
	private String qrTag;

	@Schema(description = "The global (product) order of the Stock order")
	private String orderId;

	@Schema(description = "The Producer name")
	private String producerName;

	@Schema(description = "Price paid to producer in EUR/kg")
	private BigDecimal priceToProducer;

	@Schema(description = "Price paid to farmers in EUR/kg")
	private BigDecimal priceToFarmer;

	@Schema(description = "The cupping score entered during one of the processing actions")
	private BigDecimal cuppingScore;

	@Schema(description = "The cupping flavour entered during one of the processing actions")
	private String cuppingFlavour;

	@Schema(description = "The roasting profile entered during one of the processing actions")
	private String roastingProfile;

	@Schema(description = "List of certificates of the participating companies in this Stock order")
	private List<ApiCertification> certificates;

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

	public List<ApiCertification> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ApiCertification> certificates) {
		this.certificates = certificates;
	}

	public ApiHistoryTimeline getHistoryTimeline() {
		return historyTimeline;
	}

	public void setHistoryTimeline(ApiHistoryTimeline historyTimeline) {
		this.historyTimeline = historyTimeline;
	}

}
