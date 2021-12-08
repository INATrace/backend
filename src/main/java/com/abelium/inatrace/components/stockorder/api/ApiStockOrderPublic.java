package com.abelium.inatrace.components.stockorder.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * API model for Stock order public data used in B2C page.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiStockOrderPublic {

	@ApiModelProperty(value = "The QR code tag")
	private String qrTag;

	@ApiModelProperty(value = "The global (product) order of the Stock order")
	private String orderId;

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

	public ApiHistoryTimeline getHistoryTimeline() {
		return historyTimeline;
	}

	public void setHistoryTimeline(ApiHistoryTimeline historyTimeline) {
		this.historyTimeline = historyTimeline;
	}

}
