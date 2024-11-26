package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiStockOrderHistory extends ApiBaseEntity {

	@Schema(description = "The Processing order for which the history is requested")
	private ApiProcessingOrder processingOrder;

	@Schema(description = "The Stock order for which the history is requested")
	private ApiStockOrder stockOrder;

	@Schema(description = "The Stock order output transactions")
	private List<ApiTransaction> outputTransactions;

	@Schema(description = "List of history timeline items")
	private List<ApiStockOrderHistoryTimelineItem> timelineItems;

	public ApiProcessingOrder getProcessingOrder() {
		return processingOrder;
	}

	public void setProcessingOrder(ApiProcessingOrder processingOrder) {
		this.processingOrder = processingOrder;
	}

	public ApiStockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(ApiStockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public List<ApiTransaction> getOutputTransactions() {
		if (outputTransactions == null) {
			outputTransactions = new ArrayList<>();
		}
		return outputTransactions;
	}

	public void setOutputTransactions(List<ApiTransaction> outputTransactions) {
		this.outputTransactions = outputTransactions;
	}

	public List<ApiStockOrderHistoryTimelineItem> getTimelineItems() {
		if (timelineItems == null) {
			timelineItems = new ArrayList<>();
		}
		return timelineItems;
	}

	public void setTimelineItems(List<ApiStockOrderHistoryTimelineItem> timelineItems) {
		this.timelineItems = timelineItems;
	}

}
