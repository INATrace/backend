package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.stockorder.api.ApiStockOrderEvidenceFieldValue;
import com.abelium.inatrace.db.entities.stockorder.StockOrderPEFieldValue;

/**
 * Mapper for StockOrderPEFieldValue entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class StockOrderEvidenceFieldValueMapper {

	private StockOrderEvidenceFieldValueMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiStockOrderEvidenceFieldValue toApiStockOrderEvidenceFieldValue(StockOrderPEFieldValue entity) {

		if (entity == null) {
			return null;
		}

		ApiStockOrderEvidenceFieldValue apiSOEvidenceFieldValue = new ApiStockOrderEvidenceFieldValue();
		apiSOEvidenceFieldValue.setId(entity.getId());

		apiSOEvidenceFieldValue.setEvidenceFieldId(entity.getProcessingEvidenceField().getId());
		apiSOEvidenceFieldValue.setEvidenceFieldName(entity.getProcessingEvidenceField().getFieldName());
		apiSOEvidenceFieldValue.setEvidenceFieldType(entity.getProcessingEvidenceField().getType());

		apiSOEvidenceFieldValue.setBooleanValue(entity.getBooleanValue());
		apiSOEvidenceFieldValue.setStringValue(entity.getStringValue());
		apiSOEvidenceFieldValue.setNumericValue(entity.getNumericValue());
		apiSOEvidenceFieldValue.setDateValue(entity.getInstantValue());

		return apiSOEvidenceFieldValue;
	}

}
