package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderEvidenceTypeValue;
import com.abelium.inatrace.db.entities.stockorder.StockOrderPETypeValue;
import com.abelium.inatrace.types.Language;

/**
 * Mapper for StockOrderPETypeValue entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class StockOrderEvidenceTypeValueMapper {

	private StockOrderEvidenceTypeValueMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiStockOrderEvidenceTypeValue toApiStockOrderEvidenceTypeValue(StockOrderPETypeValue entity, Long userId, Language language) {

		if (entity == null) {
			return null;
		}

		ApiStockOrderEvidenceTypeValue apiSOEvidenceTypeValue = new ApiStockOrderEvidenceTypeValue();
		apiSOEvidenceTypeValue.setEvidenceTypeId(entity.getProcessingEvidenceType().getId());
		apiSOEvidenceTypeValue.setEvidenceTypeCode(entity.getProcessingEvidenceType().getCode());
		apiSOEvidenceTypeValue.setDate(entity.getDate());
		apiSOEvidenceTypeValue.setDocument(CommonApiTools.toApiDocument(entity.getDocument(), userId));

		entity.getProcessingEvidenceType().getTranslations().stream()
				.filter(pett -> pett.getLanguage().equals(language)).findAny()
				.ifPresent(pett -> apiSOEvidenceTypeValue.setEvidenceTypeLabel(pett.getLabel()));

		return apiSOEvidenceTypeValue;
	}
}
