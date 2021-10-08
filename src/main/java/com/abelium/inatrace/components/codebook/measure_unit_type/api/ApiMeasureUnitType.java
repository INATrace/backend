package com.abelium.inatrace.components.codebook.measure_unit_type.api;

import com.abelium.inatrace.api.ApiCodebookBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * Measure unit type API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiMeasureUnitType extends ApiCodebookBaseEntity {

	@ApiModelProperty(value = "the weight of the measurement unit type", position = 3)
	private BigDecimal weight;

	@ApiModelProperty(value = "the underlying measurement unit type", position = 4)
	private ApiMeasureUnitType underlyingMeasurementUnitType;

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public ApiMeasureUnitType getUnderlyingMeasurementUnitType() {
		return underlyingMeasurementUnitType;
	}

	public void setUnderlyingMeasurementUnitType(ApiMeasureUnitType underlyingMeasurementUnitType) {
		this.underlyingMeasurementUnitType = underlyingMeasurementUnitType;
	}

}
