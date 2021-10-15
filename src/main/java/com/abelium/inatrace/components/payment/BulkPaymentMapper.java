package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.payment.api.ApiBulkPayment;
import com.abelium.inatrace.db.entities.payment.BulkPayment;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for BulkPayment entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class BulkPaymentMapper {

	private BulkPaymentMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiBulkPayment toApiBulkPayment(BulkPayment entity) {

		// Simplest apiBulkPayment object
		ApiBulkPayment apiBulkPayment = new ApiBulkPayment();
		apiBulkPayment.setId(entity.getId());
		apiBulkPayment.setCreatedBy(entity.getCreatedBy().getId());
		apiBulkPayment.setCurrency(entity.getCurrency());
		apiBulkPayment.setPaymentDescription(entity.getPaymentDescription());
		apiBulkPayment.setPaymentPurposeType(entity.getPaymentPurposeType());
		apiBulkPayment.setReceiptNumber(entity.getReceiptNumber());
		apiBulkPayment.setTotalAmount(entity.getTotalAmount());
		apiBulkPayment.setAdditionalCost(entity.getAdditionalCost());
		apiBulkPayment.setAdditionalCostDescription(entity.getAdditionalCostDescription());
		List<Long> stockOrders = new ArrayList<>();
		entity.getStockOrders().forEach((so) -> stockOrders.add(so.getId()));
		apiBulkPayment.setStockOrders(stockOrders);
		
		ApiCompanyBase apiPayingCompany = new ApiCompanyBase();
		apiPayingCompany.setId(entity.getPayingCompany().getId());
		apiPayingCompany.setName(entity.getPayingCompany().getName());
		apiBulkPayment.setPayingCompany(apiPayingCompany);

		return apiBulkPayment;
	}
}
