package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.user.UserService;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * Service for payment entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class PaymentService extends BaseService {

	@Autowired
	private UserService userService;
	
	public ApiPaginatedList<ApiPayment> getPaymentList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> paymentQueryObject(request), PaymentMapper::toApiPayment);
	}

	private Payment paymentQueryObject(ApiPaginatedRequest request) {
		
		Payment paymentProxy = Torpedo.from(Payment.class);
		QueryTools.orderBy(request.sort, paymentProxy.getId());
		return paymentProxy;
	}

	public ApiPayment getPayment(Long id) throws ApiException {
		return PaymentMapper.toApiPayment(fetchPayment(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdatePayment(ApiPayment apiPayment) throws ApiException {

		Payment entity;
		Company payingCompany;
		Company recipientCompany;
		Company paymentConfirmedByCompany;
		StockOrder stockOrder;
		User paymentConfirmedByUser;
		Document receiptDocument;
		CompanyCustomer recipientCompanyCustomer;
		UserCustomer recipientUserCustomer;

		if (apiPayment.getId() != null) {
			entity = fetchPayment(apiPayment.getId());
			payingCompany = entity.getPayingCompany();
			recipientCompany = entity.getRecipientCompany();
			paymentConfirmedByCompany = entity.getPaymentConfirmedByCompany();
			stockOrder = entity.getStockOrder();
			paymentConfirmedByUser = entity.getPaymentConfirmedByUser();
			receiptDocument = entity.getReceiptDocument();
			recipientCompanyCustomer = entity.getRecipientCompanyCustomer();
			recipientUserCustomer = entity.getRecipientUserCustomer();
		} else {
			entity = new Payment();
			payingCompany = (Company) em.createNamedQuery("Company.getCompanyById").setParameter("companyId", apiPayment.getPayingCompany().getId()).getSingleResult();
			recipientCompany = (Company) em.createNamedQuery("Company.getCompanyById").setParameter("companyId", apiPayment.getRecipientCompany().getId()).getSingleResult();
			paymentConfirmedByCompany = (Company) em.createNamedQuery("Company.getCompanyById").setParameter("companyId", apiPayment.getPaymentConfirmedByCompany().getId()).getSingleResult();
			stockOrder = (StockOrder) em.createNamedQuery("StockOrder.getStockOrderById").setParameter("stockOrderId", apiPayment.getStockOrder().getId()).getSingleResult();
			paymentConfirmedByUser = userService.fetchUserById(apiPayment.getPaymentConfirmedByUser().getId());
			receiptDocument = (Document) em.createNamedQuery("Document.getDocumentById").setParameter("documentId", apiPayment.getReceiptDocument().getId()).getSingleResult();
			recipientCompanyCustomer = (CompanyCustomer) em.createNamedQuery("CompanyCustomer.getCompanyCustomerById").setParameter("companyCustomerId", apiPayment.getRecipientCompanyCustomer().getId()).getSingleResult();
			recipientUserCustomer = (UserCustomer) em.createNamedQuery("UserCustomer.getUserCustomerById").setParameter("userCustomerId", apiPayment.getRecipientUserCustomer().getId()).getSingleResult();
		}
		
		entity.setAmount(apiPayment.getAmount());
		entity.setAmountPaidToTheCollector(apiPayment.getAmountPaidToTheCollector());
		entity.setCreatedBy(apiPayment.getCreatedBy());
		entity.setCurrency(apiPayment.getCurrency());
//		entity.setInputTransactions(null);
		entity.setStockOrder(stockOrder);
		entity.setPayingCompany(payingCompany);
		entity.setPaymentConfirmedAtTime(apiPayment.getPaymentConfirmedAtTime());
		entity.setPaymentConfirmedByCompany(paymentConfirmedByCompany);
		entity.setPaymentConfirmedByUser(paymentConfirmedByUser);
		entity.setPaymentPurporseType(apiPayment.getPaymentPurporseType());
		entity.setPaymentStatus(apiPayment.getPaymentStatus());
		entity.setPaymentType(apiPayment.getPaymentType());
		entity.setPreferredWayOfPayment(apiPayment.getPreferredWayOfPayment());
		entity.setProductionDate(apiPayment.getProductionDate());
		entity.setReceiptDocument(receiptDocument);
		entity.setReceiptNumber(apiPayment.getReceiptNumber());
		entity.setRecipientCompany(recipientCompany);
		entity.setRecipientCompanyCustomer(recipientCompanyCustomer);
		entity.setReceiptDocumentType(apiPayment.getReceiptDocumentType());
		entity.setRecipientType(apiPayment.getRecipientType());
		entity.setRecipientUserCustomer(recipientUserCustomer);
		entity.setRepresentativeOfRecipientCompany(null);
		entity.setRepresentativeOfRecipientUserCustomer(null);

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deletePayment(Long id) throws ApiException {

		Payment payment = fetchPayment(id);
		em.remove(payment);
	}

	public Payment fetchPayment(Long id) throws ApiException {

		Payment payment = Queries.get(em, Payment.class, id);
		if (payment == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid payment ID");
		}
		return payment;
	}
	
	public ApiPaginatedList<ApiPayment> listPaymentsByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<Payment> paymentsQuery = 
			em.createNamedQuery("Payment.listPaymentsByCompany", Payment.class)
				.setParameter("companyId", companyId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<Payment> payments = paymentsQuery.getResultList();

		Long count = 
			em.createNamedQuery("Payment.countPaymentsByCompany", Long.class)
				.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
			payments.stream().map(PaymentMapper::toApiPayment).collect(Collectors.toList()), count);
	}
	
}
