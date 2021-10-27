package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProductTranslation;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.codebook.SemiProductTranslation;
import com.abelium.inatrace.types.Language;

import java.util.ArrayList;

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
	 * Mapping the base entity attributes - no associations are included.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProductBase(SemiProduct entity, Language language) {

		if(entity == null) {
			return null;
		}

		SemiProductTranslation translation = entity.getSemiProductTranslations()
				.stream()
				.filter(semiProductTranslation -> semiProductTranslation.getLanguage().equals(language))
				.findFirst()
				.orElse(new SemiProductTranslation());

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(translation.getName());
		apiSemiProduct.setDescription(translation.getDescription());
		apiSemiProduct.setBuyable(entity.getBuyable());

		return apiSemiProduct;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProduct(SemiProduct entity, Language language) {

		if(entity == null) {
			return null;
		}

		ApiSemiProduct apiSemiProduct = SemiProductMapper.toApiSemiProductBase(entity, language);

		apiSemiProduct.setSKU(entity.getSKU());
		apiSemiProduct.setSKUEndCustomer(entity.getSKUEndCustomer());
		apiSemiProduct.setBuyable(entity.getBuyable());

		if (entity.getMeasurementUnitType() != null) {
			apiSemiProduct.setApiMeasureUnitType(
					MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getMeasurementUnitType()));
		}

		return apiSemiProduct;
	}

	public static ApiSemiProduct toApiSemiProductDetail(SemiProduct entity, Language language) {

		if (entity == null) {
			return null;
		}

		ApiSemiProduct apiSemiProduct = toApiSemiProduct(entity, language);
		apiSemiProduct.setTranslations(new ArrayList<>());

		entity.getSemiProductTranslations().forEach(semiProductTranslation -> {
			ApiSemiProductTranslation apiSemiProductTranslation = new ApiSemiProductTranslation();
			apiSemiProductTranslation.setName(semiProductTranslation.getName());
			apiSemiProductTranslation.setDescription(semiProductTranslation.getDescription());
			apiSemiProductTranslation.setLanguage(semiProductTranslation.getLanguage());
			apiSemiProduct.getTranslations().add(apiSemiProductTranslation);
		});

		return apiSemiProduct;
	}
}
