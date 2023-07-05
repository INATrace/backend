package com.abelium.inatrace.components.value_chain.api;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.db.entities.value_chain.enums.ValueChainStatus;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;

/**
 * Request API model object for value chain list request.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiValueChainListRequest extends ApiPaginatedRequest {

	@ApiParam(value = "Value chain name")
	private String name;

	@ApiParam(value = "Value chain status")
	private ValueChainStatus valueChainStatus;

	@ApiParam(value = "Value chain product type ID")
	private Long productTypeId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValueChainStatus getValueChainStatus() {
		return valueChainStatus;
	}

	public void setValueChainStatus(ValueChainStatus valueChainStatus) {
		this.valueChainStatus = valueChainStatus;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
}
