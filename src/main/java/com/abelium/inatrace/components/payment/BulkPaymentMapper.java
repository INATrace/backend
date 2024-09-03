package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.common.mappers.ActivityProofMapper;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.payment.api.ApiBulkPayment;
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

	public static ApiBulkPayment toApiBulkPaymentBase(BulkPayment entity, Long userId) {
		if(entity == null) {
			return null;
		}

		ApiBulkPayment apiBulkPayment = new ApiBulkPayment();
		apiBulkPayment.setId(entity.getId());
		apiBulkPayment.setPaymentPurposeType(entity.getPaymentPurposeType());
		apiBulkPayment.setReceiptNumber(entity.getReceiptNumber());
		apiBulkPayment.setTotalAmount(entity.getTotalAmount());
		apiBulkPayment.setCurrency(entity.getCurrency());
		return apiBulkPayment;
	}

	public static ApiBulkPayment toApiBulkPayment(BulkPayment entity, Long userId) {
		if(entity == null) {
			return null;
		}

		ApiBulkPayment apiBulkPayment = new ApiBulkPayment();
		apiBulkPayment.setId(entity.getId());
		apiBulkPayment.setCreatedBy(UserMapper.toSimpleApiUser(entity.getCreatedBy()));
		apiBulkPayment.setCreationTimestamp(entity.getCreationTimestamp());
		apiBulkPayment.setFormalCreationTime(entity.getFormalCreationTime());
		apiBulkPayment.setCurrency(entity.getCurrency());
		apiBulkPayment.setPaymentDescription(entity.getPaymentDescription());
		apiBulkPayment.setPaymentPurposeType(entity.getPaymentPurposeType());
		apiBulkPayment.setReceiptNumber(entity.getReceiptNumber());
		apiBulkPayment.setTotalAmount(entity.getTotalAmount());
		apiBulkPayment.setAdditionalCost(entity.getAdditionalCost());
		apiBulkPayment.setAdditionalCostDescription(entity.getAdditionalCostDescription());
		apiBulkPayment.setPayments(entity.getPayments().stream().map(p -> PaymentMapper.toApiPayment(p, userId)).collect(Collectors.toList()));
		apiBulkPayment.setPayingCompany(CompanyMapper.toApiCompanyBase(entity.getPayingCompany()));

		if (!entity.getAdditionalProofs().isEmpty()) {
			apiBulkPayment.setAdditionalProofs(entity.getAdditionalProofs().stream()
					.map(ap -> ActivityProofMapper.toApiActivityProof(ap.getActivityProof(), userId))
					.collect(Collectors.toList()));
		}

		return apiBulkPayment;
	}
}
