package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.payment.api.ApiBulkPayment;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.user.mappers.UserMapper;
import com.abelium.inatrace.db.entities.payment.BulkPayment;

import java.util.stream.Collectors;

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
		if(entity == null) return null;

		ApiBulkPayment apiBulkPayment = new ApiBulkPayment();
		apiBulkPayment.setId(entity.getId());
		apiBulkPayment.setCreatedBy(UserMapper.toSimpleApiUser(entity.getCreatedBy()));
		apiBulkPayment.setCurrency(entity.getCurrency());
		apiBulkPayment.setPaymentDescription(entity.getPaymentDescription());
		apiBulkPayment.setPaymentPurposeType(entity.getPaymentPurposeType());
		apiBulkPayment.setReceiptNumber(entity.getReceiptNumber());
		apiBulkPayment.setTotalAmount(entity.getTotalAmount());
		apiBulkPayment.setAdditionalCost(entity.getAdditionalCost());
		apiBulkPayment.setAdditionalCostDescription(entity.getAdditionalCostDescription());
		apiBulkPayment.setStockOrders(entity.getStockOrders().stream().map(StockOrderMapper::toApiStockOrderBase).collect(Collectors.toList()));
		apiBulkPayment.setPayingCompany(CompanyMapper.toApiCompanyBase(entity.getPayingCompany()));
		return apiBulkPayment;
	}
}
