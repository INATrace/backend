package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiStockOrderHistoryTimelineItem extends ApiBaseEntity {

	@ApiModelProperty(value = "The Processing order representing this processing step (used when not Purchase order)")
	private ApiProcessingOrder processingOrder;

	@ApiModelProperty(value = "Stock order representing this processing step (used in Purchase orders)")
	private ApiStockOrder stockOrder;

	@ApiModelProperty(value = "Depth of aggregation history")
	private Integer depth;

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

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
