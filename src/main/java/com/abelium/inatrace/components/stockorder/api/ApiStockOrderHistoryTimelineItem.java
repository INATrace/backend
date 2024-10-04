package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiStockOrderHistoryTimelineItem extends ApiBaseEntity {

	@Schema(description = "The Processing order representing this processing step (used when not Purchase order)")
	private ApiProcessingOrder processingOrder;

	@Schema(description = "Processing evidence types stored values for this stock order")
	private List<ApiStockOrderEvidenceTypeValue> requiredEvidenceDocuments;

	@Schema(description = "Other processing evidence documents - evidence types that can be provided but are not mandatory")
	private List<ApiStockOrderEvidenceTypeValue> otherEvidenceDocuments;

	@Schema(description = "Stock order representing this processing step (used in Purchase orders)")
	private ApiStockOrder stockOrder;

	@Schema(description = "Aggregated Purchase orders")
	private List<ApiStockOrder> purchaseOrders;

	@Schema(description = "Depth of aggregation history")
	private Integer depth;

	public ApiProcessingOrder getProcessingOrder() {
		return processingOrder;
	}

	public void setProcessingOrder(ApiProcessingOrder processingOrder) {
		this.processingOrder = processingOrder;
	}

	public List<ApiStockOrderEvidenceTypeValue> getRequiredEvidenceDocuments() {
		if (requiredEvidenceDocuments == null) {
			requiredEvidenceDocuments = new ArrayList<>();
		}
		return requiredEvidenceDocuments;
	}

	public void setRequiredEvidenceDocuments(List<ApiStockOrderEvidenceTypeValue> requiredEvidenceDocuments) {
		this.requiredEvidenceDocuments = requiredEvidenceDocuments;
	}

	public List<ApiStockOrderEvidenceTypeValue> getOtherEvidenceDocuments() {
		if (otherEvidenceDocuments == null) {
			otherEvidenceDocuments = new ArrayList<>();
		}
		return otherEvidenceDocuments;
	}

	public void setOtherEvidenceDocuments(List<ApiStockOrderEvidenceTypeValue> otherEvidenceDocuments) {
		this.otherEvidenceDocuments = otherEvidenceDocuments;
	}

	public ApiStockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(ApiStockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public List<ApiStockOrder> getPurchaseOrders() {
		if (purchaseOrders == null) {
			purchaseOrders = new ArrayList<>();
		}
		return purchaseOrders;
	}

	public void setPurchaseOrders(List<ApiStockOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
