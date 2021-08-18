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

	public static ApiSemiProduct toApiSemiProduct(SemiProduct entity) {

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();

		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(entity.getName());
		apiSemiProduct.setDescription(entity.getDescription());
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
