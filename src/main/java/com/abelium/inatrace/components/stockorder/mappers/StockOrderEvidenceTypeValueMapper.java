package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderEvidenceTypeValue;
import com.abelium.inatrace.db.entities.stockorder.StockOrderPETypeValue;

/**
 * Mapper for StockOrderPETypeValue entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class StockOrderEvidenceTypeValueMapper {

	private StockOrderEvidenceTypeValueMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiStockOrderEvidenceTypeValue toApiStockOrderEvidenceTypeValue(StockOrderPETypeValue entity, Long userId) {

		if (entity == null) {
			return null;
		}

		ApiStockOrderEvidenceTypeValue apiSOEvidenceTypeValue = new ApiStockOrderEvidenceTypeValue();
		apiSOEvidenceTypeValue.setEvidenceTypeId(entity.getProcessingEvidenceType().getId());
		apiSOEvidenceTypeValue.setEvidenceTypedCode(entity.getProcessingEvidenceType().getCode());
		apiSOEvidenceTypeValue.setDate(entity.getDate());
		apiSOEvidenceTypeValue.setDocument(CommonApiTools.toApiDocument(entity.getDocument(), userId));

		return apiSOEvidenceTypeValue;
	}
}
