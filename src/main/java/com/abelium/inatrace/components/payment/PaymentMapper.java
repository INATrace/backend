package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
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
		apiPayment.setCreatedBy(entity.getCreatedBy());
		apiPayment.setPaymentType(entity.getPaymentType());
		apiPayment.setCurrency(entity.getCurrency());
		apiPayment.setAmount(entity.getAmount());
		apiPayment.setAmountPaidToTheCollector(entity.getAmountPaidToTheCollector());
		apiPayment.setRecipientType(entity.getRecipientType());
		apiPayment.setReceiptNumber(entity.getReceiptNumber());
		apiPayment.setReceiptDocumentType(entity.getReceiptDocumentType());
		apiPayment.setPaymentPurporseType(entity.getPaymentPurporseType());
		apiPayment.setPaymentStatus(entity.getPaymentStatus());
		apiPayment.setPaymentConfirmedAtTime(entity.getPaymentConfirmedAtTime());
		apiPayment.setPreferredWayOfPayment(entity.getPreferredWayOfPayment());
		apiPayment.setProductionDate(entity.getProductionDate());
		
		ApiStockOrder apiStockOrder = new ApiStockOrder();
		apiStockOrder.setId(entity.getStockOrder().getId());
		apiPayment.setStockOrder(apiStockOrder);
		
		ApiCompanyBase apiPayingCompany = new ApiCompanyBase();
		apiPayingCompany.setId(entity.getPayingCompany().getId());
		apiPayingCompany.setName(entity.getPayingCompany().getName());
		apiPayment.setPayingCompany(apiPayingCompany);
		
		ApiCompanyBase apiRecipientCompany = new ApiCompanyBase();
		apiRecipientCompany.setId(entity.getRecipientCompany().getId());
		apiRecipientCompany.setName(entity.getRecipientCompany().getName());
		apiPayment.setRecipientCompany(apiRecipientCompany);
		
		ApiUserCustomer apiRecipientUserCustomer = new ApiUserCustomer();
		apiRecipientUserCustomer.setId(entity.getRecipientUserCustomer().getId());
		apiRecipientUserCustomer.setName(entity.getRecipientUserCustomer().getName());
		apiPayment.setRecipientUserCustomer(apiRecipientUserCustomer);
		
		ApiCompanyCustomer apiRecipientCompanyCustomer = new ApiCompanyCustomer();
		apiRecipientCompanyCustomer.setId(entity.getRecipientCompanyCustomer().getId());
		apiRecipientCompanyCustomer.setName(entity.getRecipientCompanyCustomer().getName());
		apiPayment.setRecipientCompanyCustomer(apiRecipientCompanyCustomer);
		
		ApiDocument apiReceiptDocument = new ApiDocument();
		apiReceiptDocument.setId(entity.getReceiptDocument().getId());
		apiReceiptDocument.setName(entity.getReceiptDocument().getName());
		apiPayment.setReceiptDocument(apiReceiptDocument);
		
		ApiUser apiPaymentConfirmedByUser = new ApiUser();
		apiPaymentConfirmedByUser.setId(entity.getPaymentConfirmedByUser().getId());
		apiPaymentConfirmedByUser.setName(entity.getPaymentConfirmedByUser().getName());
		apiPayment.setPaymentConfirmedByUser(apiPaymentConfirmedByUser);
		
		ApiCompanyBase apiPaymentConfirmedByCompany = new ApiCompanyBase();
		apiPaymentConfirmedByCompany.setId(entity.getPaymentConfirmedByCompany().getId());
		apiPaymentConfirmedByCompany.setName(entity.getPaymentConfirmedByCompany().getName());
		apiPayment.setPaymentConfirmedByCompany(apiPaymentConfirmedByCompany);

		return apiPayment;
	}
}
