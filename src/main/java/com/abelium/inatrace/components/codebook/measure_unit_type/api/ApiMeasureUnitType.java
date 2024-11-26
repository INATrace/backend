package com.abelium.inatrace.components.codebook.measure_unit_type.api;

import com.abelium.inatrace.api.ApiCodebookBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * Measure unit type API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiMeasureUnitType extends ApiCodebookBaseEntity {

	@Schema(description = "the weight of the measurement unit type")
	private BigDecimal weight;

	@Schema(description = "the underlying measurement unit type")
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
