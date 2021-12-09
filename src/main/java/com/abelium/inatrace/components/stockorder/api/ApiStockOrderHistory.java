package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Validated
public class ApiStockOrderHistory extends ApiBaseEntity {

	@ApiModelProperty(value = "The Processing order for which the history is requested")
	private ApiProcessingOrder processingOrder;

	@ApiModelProperty(value = "The Stock order for which the history is requested")
	private ApiStockOrder stockOrder;

	@ApiModelProperty(value = "List of history timeline items")
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
