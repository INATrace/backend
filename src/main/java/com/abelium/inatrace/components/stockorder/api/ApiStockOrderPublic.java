package com.abelium.inatrace.components.stockorder.api;

/**
 * API model for Stock order public data used in B2C page.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiStockOrderPublic {

	private String qrTag;

	private String orderId;

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

}
