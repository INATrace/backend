package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;

/**
 * Mapper for SemiProduct entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class SemiProductMapper {

	private SemiProductMapper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Mapping ID and name to API entity
	 *
	 * @param entity DB entity
	 * @return API model entity with ID and name
	 */
	public static ApiSemiProduct toApiSemiProductIdName(SemiProduct entity) {

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(entity.getName());

		return apiSemiProduct;
	}

	/**
	 * Mapping the base entity attributes - no associations are included.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProductBase(SemiProduct entity) {

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(entity.getName());
		apiSemiProduct.setDescription(entity.getDescription());

		return apiSemiProduct;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProduct(SemiProduct entity) {

		ApiSemiProduct apiSemiProduct = SemiProductMapper.toApiSemiProductBase(entity);

		apiSemiProduct.setSKU(entity.getSKU());
		apiSemiProduct.setSKUEndCustomer(entity.getSKUEndCustomer());
		apiSemiProduct.setBuyable(entity.getBuyable());

		if (entity.getMeasurementUnitType() != null) {
			apiSemiProduct.setApiMeasureUnitType(
					MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getMeasurementUnitType()));
		}

		return apiSemiProduct;
	}
}
