package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiStockOrderAggregation extends ApiBaseEntity {

	@ApiModelProperty(value = "Stock order related to the aggregation")
	private ApiStockOrder stockOrder;

	// TODO: add fields and documents in aggregation

	public ApiStockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(ApiStockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}
}
