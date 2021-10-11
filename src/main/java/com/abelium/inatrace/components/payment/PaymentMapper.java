package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.db.entities.payment.Payment;

/**
 * Mapper for Payment entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class PaymentMapper {

	private PaymentMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiPayment toApiPayment(Payment entity) {

		// Simplest apiPayment object
		ApiPayment apiPayment = new ApiPayment();
		apiPayment.setId(entity.getId());
		apiPayment.setCreatedBy(entity.getCreatedBy().getId());
		apiPayment.setPaymentType(entity.getPaymentType());
		apiPayment.setCurrency(entity.getCurrency());
		apiPayment.setAmountPaidToTheFarmer(entity.getAmountPaidToTheFarmer());
		apiPayment.setAmountPaidToTheCollector(entity.getAmountPaidToTheCollector());
		apiPayment.setRecipientType(entity.getRecipientType());
		apiPayment.setReceiptNumber(entity.getReceiptNumber());
		apiPayment.setReceiptDocumentType(entity.getReceiptDocumentType());
		apiPayment.setPaymentPurporseType(entity.getPaymentPurposeType());
		apiPayment.setPaymentStatus(entity.getPaymentStatus());
		apiPayment.setPaymentConfirmedAtTime(entity.getPaymentConfirmedAtTime());
		apiPayment.setPreferredWayOfPayment(entity.getPreferredWayOfPayment());
		apiPayment.setProductionDate(entity.getProductionDate());
		
		ApiStockOrder apiStockOrder = new ApiStockOrder();
		apiStockOrder.setId(entity.getStockOrder().getId());
		apiStockOrder.setIdentifier(entity.getStockOrder().getIdentifier());
		apiPayment.setStockOrder(apiStockOrder);
		
		ApiCompanyBase apiRecipientCompany = new ApiCompanyBase();
		apiRecipientCompany.setId(entity.getRecipientCompany().getId());
		apiRecipientCompany.setName(entity.getRecipientCompany().getName());
		apiPayment.setRecipientCompany(apiRecipientCompany);
		
//		ApiCompanyCustomer apiRecipientCompanyCustomer = new ApiCompanyCustomer();
//		apiRecipientCompanyCustomer.setId(entity.getRecipientCompanyCustomer().getId());
//		apiRecipientCompanyCustomer.setName(entity.getRecipientCompanyCustomer().getName());
//		apiPayment.setRecipientCompanyCustomer(apiRecipientCompanyCustomer);
		
		ApiDocument apiReceiptDocument = new ApiDocument();
		apiReceiptDocument.setId(entity.getReceiptDocument().getId());
		apiReceiptDocument.setName(entity.getReceiptDocument().getName());
		apiReceiptDocument.setStorageKey(entity.getReceiptDocument().getStorageKey());
		apiPayment.setReceiptDocument(apiReceiptDocument);
		
		ApiUser apiPaymentConfirmedByUser = new ApiUser();
		apiPaymentConfirmedByUser.setId(entity.getPaymentConfirmedByUser().getId());
		apiPaymentConfirmedByUser.setName(entity.getPaymentConfirmedByUser().getName());
		apiPayment.setPaymentConfirmedByUser(apiPaymentConfirmedByUser);
		
		return apiPayment;
	}
}
