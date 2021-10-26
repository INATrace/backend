package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ApiStockOrderAggregatedHistory extends ApiBaseEntity {

	@ApiModelProperty(value = "Processing order for this aggregation history")
	private ApiProcessingOrder processingOrder;


	@ApiModelProperty(value = "List of fields for this aggregation history")
	private List<ApiStockOrderAggregation> aggregations;

	@ApiModelProperty(value = "Depth of aggregation history")
	private Integer depth;

	public ApiProcessingOrder getProcessingOrder() {
		return processingOrder;
	}

	public void setProcessingOrder(ApiProcessingOrder processingOrder) {
		this.processingOrder = processingOrder;
	}

	public List<ApiStockOrderAggregation> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<ApiStockOrderAggregation> aggregations) {
		this.aggregations = aggregations;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
}
