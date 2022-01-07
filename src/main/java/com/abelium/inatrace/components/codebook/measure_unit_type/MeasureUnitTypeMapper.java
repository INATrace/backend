package com.abelium.inatrace.components.codebook.measure_unit_type;

import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;

/**
 * Mapper for MeasureUnitType entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class MeasureUnitTypeMapper {

	private MeasureUnitTypeMapper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Mapping the base entity attributes - no associations are included.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiMeasureUnitType toApiMeasureUnitTypeBase(MeasureUnitType entity) {
		if(entity == null) return null;
		ApiMeasureUnitType apiMeasureUnitType = new ApiMeasureUnitType();
		apiMeasureUnitType.setId(entity.getId());
		apiMeasureUnitType.setCode(entity.getCode());
		apiMeasureUnitType.setLabel(entity.getLabel());

		return apiMeasureUnitType;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiMeasureUnitType toApiMeasureUnitType(MeasureUnitType entity) {

		if (entity == null) {
			return null;
		}

		ApiMeasureUnitType apiMeasureUnitType = MeasureUnitTypeMapper.toApiMeasureUnitTypeBase(entity);

		apiMeasureUnitType.setWeight(entity.getWeight());

		if (entity.getUnderlyingMeasurementUnitType() != null) {
			apiMeasureUnitType.setUnderlyingMeasurementUnitType(
					MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getUnderlyingMeasurementUnitType()));
		}

		return apiMeasureUnitType;
	}
}
