package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.user.mappers.UserMapper;
import com.abelium.inatrace.components.usercustomer.mappers.UserCustomerMapper;
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
		apiPayment.setUpdatedTimestamp(entity.getUpdateTimestamp());
		apiPayment.setCreatedBy(UserMapper.toSimpleApiUser(entity.getCreatedBy()));
		apiPayment.setUpdatedBy(UserMapper.toSimpleApiUser(entity.getUpdatedBy()));
		apiPayment.setPaymentType(entity.getPaymentType());
		apiPayment.setCurrency(entity.getCurrency());
		apiPayment.setAmountPaidToTheFarmer(entity.getAmountPaidToTheFarmer());
		apiPayment.setAmountPaidToTheCollector(entity.getAmountPaidToTheCollector());
		apiPayment.setRecipientType(entity.getRecipientType());
		apiPayment.setReceiptNumber(entity.getReceiptNumber());
		apiPayment.setReceiptDocumentType(entity.getReceiptDocumentType());
		apiPayment.setPaymentPurposeType(entity.getPaymentPurposeType());
		apiPayment.setPaymentStatus(entity.getPaymentStatus());
		apiPayment.setPaymentConfirmedAtTime(entity.getPaymentConfirmedAtTime());
		apiPayment.setFormalCreationTime(entity.getFormalCreationTime());
		apiPayment.setPreferredWayOfPayment(entity.getPreferredWayOfPayment());
		apiPayment.setProductionDate(entity.getProductionDate());
		apiPayment.setRecipientUserCustomer(UserCustomerMapper.toApiUserCustomerBase(entity.getRecipientUserCustomer()));
		apiPayment.setRecipientCompany(CompanyMapper.toApiCompanyBase(entity.getRecipientCompany()));
		apiPayment.setPayingCompany(CompanyMapper.toApiCompanyBase(entity.getPayingCompany()));
		apiPayment.setPaymentConfirmedByUser(UserMapper.toSimpleApiUser(entity.getPaymentConfirmedByUser()));
		apiPayment.setStockOrder(StockOrderMapper.toApiStockOrderBase(entity.getStockOrder()));

//		ApiCompanyCustomer apiRecipientCompanyCustomer = new ApiCompanyCustomer();
//		apiRecipientCompanyCustomer.setId(entity.getRecipientCompanyCustomer().getId());
//		apiRecipientCompanyCustomer.setName(entity.getRecipientCompanyCustomer().getName());
//		apiPayment.setRecipientCompanyCustomer(apiRecipientCompanyCustomer);

		if (entity.getReceiptDocument() != null) {
			ApiDocument apiReceiptDocument = new ApiDocument();
			apiReceiptDocument.setId(entity.getReceiptDocument().getId());
			apiReceiptDocument.setName(entity.getReceiptDocument().getName());
			apiReceiptDocument.setStorageKey(entity.getReceiptDocument().getStorageKey());
			apiPayment.setReceiptDocument(apiReceiptDocument);
		}

		return apiPayment;
	}
}
