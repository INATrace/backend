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
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public ApiPaginatedList<ApiPayment> getPaymentList(ApiPaginatedRequest request, PaymentQueryRequest queryRequest) {

		return PaginationTools.createPaginatedResponse(em, request, () -> paymentQueryObject(request, queryRequest), PaymentMapper::toApiPayment);
	}

	private Payment paymentQueryObject(ApiPaginatedRequest request, PaymentQueryRequest queryRequest) {

		Payment paymentProxy = Torpedo.from(Payment.class);
		OnGoingLogicalCondition condition = Torpedo.condition();

		// Query parameter filters
		if(queryRequest.paymentStatus != null)
			condition.and(paymentProxy.getPaymentStatus()).eq(queryRequest.paymentStatus);
		if(queryRequest.preferredWayOfPayment != null)
			condition.and(paymentProxy.getPreferredWayOfPayment()).eq(queryRequest.preferredWayOfPayment);
		if(queryRequest.productionDateStart != null)
			condition.and(paymentProxy.getProductionDate()).gte(queryRequest.productionDateStart);
		if(queryRequest.productionDateEnd != null)
			condition.and(paymentProxy.getProductionDate()).lte(queryRequest.productionDateEnd);
		if(queryRequest.farmerName != null) // Search by farmers name (query)
			condition.and(paymentProxy.getRecipientUserCustomer()).isNotNull()
					.and(paymentProxy.getRecipientUserCustomer().getName() + " " + paymentProxy.getRecipientUserCustomer().getSurname())
					.like().startsWith(queryRequest.farmerName);

		QueryTools.orderBy(request.sort, paymentProxy.getId());

		return paymentProxy;
	}

	public ApiPayment getPayment(Long id) throws ApiException {
		return PaymentMapper.toApiPayment(fetchPayment(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdatePayment(ApiPayment apiPayment, Long userId) throws ApiException {

		Payment entity;
		Company payingCompany = null;
		Company recipientCompany = null;
		Company paymentConfirmedByCompany = null;
		StockOrder stockOrder = null;
		Document receiptDocument = null;
		User paymentConfirmedByUser = null;
		UserCustomer payableToCollector = null;
		UserCustomer payableToFarmer = null;
		CompanyCustomer recipientCompanyCustomer = null; // still don't know where to get it from - assign null for now
		PreferredWayOfPayment preferredWayOfPayment = null;
		String orderReference = null;
		Integer purchased = null;
		Integer openBalance = null;
		Integer totalPaid = null;

		if (apiPayment.getId() != null) {

			// If method is PUT, let's keep it simple and just allow to update the payment status to CONFIRMED
			// Coffee Matheo doesn't allow us to update fields other than the payment status
			// Also it is not allowed to set back the value to UNCONFIRMED
			entity = fetchPayment(apiPayment.getId());
			if (apiPayment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
				if (entity.getPaymentStatus() == PaymentStatus.UNCONFIRMED) {
					entity.setPaymentStatus(PaymentStatus.CONFIRMED);
				}
			}
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setUpdatedBy(userService.fetchUserById(apiPayment.getUpdatedBy()));

			// TODO: Should the "paymentConfirmedByCompany" be the same as "payingCompany"?
			entity.setPaymentConfirmedByCompany(entity.getPayingCompany());
			entity.setPaymentConfirmedByUser(userService.fetchUserById(userId));
			entity.setPaymentConfirmedAtTime(Instant.now());

		} else {
			
			// If method is POST, let's create a payment from scratch
			// Important to consider that a purchase order must exist, since we will extract information from it
			entity = new Payment();
			// Get purchase order to which you will generate a payment
			stockOrder = (StockOrder) em.createNamedQuery("StockOrder.getPurchaseOrderById")
					.setParameter("stockOrderId", apiPayment.getStockOrder().getId())
					.getSingleResult();
			if (stockOrder != null) {
				
				// the company who creates a purchase should be the one who pays for it, right?
				payingCompany = stockOrder.getCompany();
				// company who receives the payment - could this be client property on stock order?
				recipientCompany = (Company) em.createNamedQuery("Company.getCompanyById")
						.setParameter("companyId", apiPayment.getRecipientCompany().getId())
						.getSingleResult();
				// collector (representative) who is getting the payment
				payableToCollector = stockOrder.getRepresentativeOfProducerUserCustomer();
				// farmer who is getting the payment
				payableToFarmer = stockOrder.getProducerUserCustomer();
				// purchase order identifier
				orderReference = stockOrder.getIdentifier();
				// purchase order preferred way of payment
				preferredWayOfPayment = stockOrder.getPreferredWayOfPayment();
				// purchase order total quantity of semi-product
				purchased = stockOrder.getTotalQuantity();
				// purchase order open balance
				openBalance = stockOrder.getBalance().intValue();
				// amount paid to the farmer
				totalPaid = apiPayment.getAmountPaidToTheFarmer();

			} else {
				throw new ApiException(ApiStatus.INVALID_REQUEST, "A purchase order is required in order to create a payment.");
			}
			
			// Storage key needs to be unique
			receiptDocument = new Document();
			receiptDocument.setContentType(apiPayment.getReceiptDocument().getContentType());
			receiptDocument.setName(apiPayment.getReceiptDocument().getName());
			receiptDocument.setSize(apiPayment.getReceiptDocument().getSize());
			receiptDocument.setStorageKey(apiPayment.getReceiptDocument().getStorageKey());
			
			entity.setAmountPaidToTheFarmer(totalPaid);
			entity.setAmountPaidToTheCollector(0);
			entity.setCreatedBy(userService.fetchUserById(apiPayment.getCreatedBy()));
			entity.setUpdatedBy(userService.fetchUserById(apiPayment.getUpdatedBy()));
			entity.setCurrency(apiPayment.getCurrency());
//			entity.setInputTransactions(null);
			entity.setStockOrder(stockOrder);
			entity.setOrderReference(orderReference);
			entity.setPayingCompany(payingCompany);
			entity.setPaymentConfirmedAtTime(apiPayment.getPaymentConfirmedAtTime());
			entity.setPaymentConfirmedByCompany(paymentConfirmedByCompany);
			entity.setPaymentConfirmedByUser(paymentConfirmedByUser);
			entity.setPaymentPurposeType(apiPayment.getPaymentPurposeType());
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setPaymentType(apiPayment.getPaymentType());
			entity.setPreferredWayOfPayment(preferredWayOfPayment);
//			entity.setProductionDate(apiPayment.getProductionDate());
			entity.setPurchased(purchased);
			entity.setReceiptDocument(receiptDocument);
			entity.setReceiptNumber(apiPayment.getReceiptNumber());
			entity.setRecipientCompany(recipientCompany);
			entity.setRecipientCompanyCustomer(recipientCompanyCustomer);
			entity.setReceiptDocumentType(apiPayment.getReceiptDocumentType());
			entity.setRecipientType(apiPayment.getRecipientType());
			entity.setRecipientUserCustomer(payableToFarmer);
			entity.setRepresentativeOfRecipientCompany(recipientCompany); // is this required? seems to be same as recipientCompany
			entity.setRepresentativeOfRecipientUserCustomer(payableToCollector);
			entity.setTotalPaid(totalPaid);
			
			// do not forget to update the stock order entity - open balance - a negative open balance is allowed
			stockOrder.setBalance(new BigDecimal(openBalance - entity.getTotalPaid()));
			stockOrder.setUpdatedBy(userService.fetchUserById(apiPayment.getUpdatedBy()));
		}
		
		if (entity.getId() == null) {
			em.persist(entity);
			em.persist(stockOrder);
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
	
	public ApiPaginatedList<ApiPayment> listPaymentsByPurchase(Long purchaseId, ApiPaginatedRequest request) {

		TypedQuery<Payment> paymentsQuery = 
			em.createNamedQuery("Payment.listPaymentsByPurchaseId", Payment.class)
				.setParameter("purchaseId", purchaseId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<Payment> payments = paymentsQuery.getResultList();

		Long count = 
			em.createNamedQuery("Payment.countPaymentsByPurchaseId", Long.class)
				.setParameter("purchaseId", purchaseId).getSingleResult();

		return new ApiPaginatedList<>(
			payments.stream().map(PaymentMapper::toApiPayment).collect(Collectors.toList()), count);
	}
	
	public ApiPaginatedList<ApiPayment> listPaymentsByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<Payment> paymentsQuery = 
			em.createNamedQuery("Payment.listPaymentsByCompanyId", Payment.class)
				.setParameter("companyId", companyId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<Payment> payments = paymentsQuery.getResultList();

		Long count = 
			em.createNamedQuery("Payment.countPaymentsByCompanyId", Long.class)
				.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
			payments.stream().map(PaymentMapper::toApiPayment).collect(Collectors.toList()), count);
	}
	
}
