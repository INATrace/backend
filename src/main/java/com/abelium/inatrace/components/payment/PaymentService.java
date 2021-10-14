package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.user.UserService;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.Instant;

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
		return PaginationTools.createPaginatedResponse(em, request, () -> paymentQueryObject(
				request, queryRequest), PaymentMapper::toApiPayment);

	}

	private Payment paymentQueryObject(ApiPaginatedRequest request, PaymentQueryRequest queryRequest) {

		Payment paymentProxy = Torpedo.from(Payment.class);
		OnGoingLogicalCondition condition = Torpedo.condition();

		// Applies only when fetching list by PurchaseID or CompanyID
		if(queryRequest.companyId != null) {
			condition.and(paymentProxy.getStockOrder().getCompany().getId()).eq(queryRequest.companyId);
		} else if (queryRequest.purchaseId != null)
			condition.and(paymentProxy.getStockOrder().getId()).eq(queryRequest.purchaseId);

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
					.like().any(queryRequest.farmerName);

		Torpedo.where(condition);

		switch (request.sortBy) {
			case "productionDate": QueryTools.orderBy(request.sort, paymentProxy.getProductionDate()); break;
			default: QueryTools.orderBy(request.sort, paymentProxy.getId());
		}

		return paymentProxy;
	}

	public ApiPayment getPayment(Long id) throws ApiException {
		return PaymentMapper.toApiPayment(fetchEntity(id, Payment.class));
	}

	@Transactional
	public ApiBaseEntity createOrUpdatePayment(ApiPayment apiPayment, Long userId) throws ApiException {

		Payment entity = fetchEntityOrElse(apiPayment.getId(), Payment.class, new Payment());
		User currentUser = userService.fetchUserById(userId);

		if (entity.getId() != null) {

			// If method is PUT, let's keep it simple and just allow to update the payment status to CONFIRMED
			// Coffee Matheo doesn't allow us to update fields other than the payment status
			// Also it is not allowed to set back the value to UNCONFIRMED
			if (apiPayment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
				if (entity.getPaymentStatus() == PaymentStatus.UNCONFIRMED) {
					entity.setPaymentStatus(PaymentStatus.CONFIRMED);
				}
			}
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setUpdatedBy(userService.fetchUserById(apiPayment.getUpdatedBy()));

			// TODO: Should the "paymentConfirmedByCompany" be the same as "payingCompany"?
			entity.setPaymentConfirmedAtTime(Instant.now());
			entity.setPaymentConfirmedByCompany(entity.getPayingCompany());
			entity.setPaymentConfirmedByUser(currentUser);
			entity.setUpdatedBy(currentUser);

		} else {

			// If method is POST, let's create a payment from scratch
			// Important to consider that a purchase order must exist, since we will extract information from it
			// Get purchase order to which you will generate a payment
			StockOrder stockOrder;
			try {
				stockOrder = (StockOrder) em.createNamedQuery("StockOrder.getPurchaseOrderById")
						.setParameter("stockOrderId", apiPayment.getStockOrder().getId())
						.getSingleResult();
			} catch (NoResultException e) {
				throw new ApiException(ApiStatus.INVALID_REQUEST, "A purchase order (StockOrder) is required in order to create a payment.");
			}

			// Values from StockOrder
			entity.setStockOrder(stockOrder);
			entity.setOrderReference(stockOrder.getIdentifier());
			entity.setProductionDate(stockOrder.getProductionDate()); // TODO: Is productionDate same as StockOrder prod. date?
			entity.setPayingCompany(stockOrder.getCompany()); // same company which has created purchase order
			entity.setPurchased(stockOrder.getTotalQuantity());
			entity.setPreferredWayOfPayment(stockOrder.getPreferredWayOfPayment());
			entity.setRecipientUserCustomer(stockOrder.getProducerUserCustomer()); // farmer
			entity.setRepresentativeOfRecipientUserCustomer(stockOrder.getRepresentativeOfProducerUserCustomer()); // collector

			// Storage key needs to be unique
//			Document receiptDocument = new Document();
//			receiptDocument.setContentType(apiPayment.getReceiptDocument().getContentType());
//			receiptDocument.setName(apiPayment.getReceiptDocument().getName());
//			receiptDocument.setSize(apiPayment.getReceiptDocument().getSize());
//			receiptDocument.setStorageKey(apiPayment.getReceiptDocument().getStorageKey());
//			entity.setReceiptDocument(receiptDocument);

			entity.setReceiptDocumentType(apiPayment.getReceiptDocumentType());
			entity.setPaymentConfirmedAtTime(apiPayment.getPaymentConfirmedAtTime());
			entity.setFormalCreationTime(apiPayment.getFormalCreationTime());
			entity.setPaymentPurposeType(apiPayment.getPaymentPurposeType());
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setPaymentType(apiPayment.getPaymentType());
			entity.setReceiptNumber(apiPayment.getReceiptNumber());
			entity.setRecipientType(apiPayment.getRecipientType());
			entity.setCurrency(apiPayment.getCurrency());

			entity.setTotalPaid(apiPayment.getAmountPaidToTheFarmer()); // ?
			entity.setAmountPaidToTheFarmer(apiPayment.getAmountPaidToTheFarmer());
			entity.setAmountPaidToTheCollector(0); // ?

			// TODO: Optional ? -> Or should be error thrown?
			if (apiPayment.getRecipientCompany() != null)
				entity.setRecipientCompany(fetchEntityOrElse(apiPayment.getRecipientCompany().getId(), Company.class, null));
			if (apiPayment.getRepresentativeOfRecipientCompany() != null)
				entity.setRecipientCompany(fetchEntityOrElse(apiPayment.getRepresentativeOfRecipientCompany().getId(), Company.class, null));

			entity.setCreatedBy(currentUser);
			entity.setUpdatedBy(currentUser);

			// TODO:  do not forget to update the stock order entity - open balance - a negative open balance is allowed
//			stockOrder.setBalance(stockOrder.getBalance().subtract(BigDecimal.valueOf(entity.getTotalPaid())));
			stockOrder.setUpdatedBy(currentUser);
		}
		
		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deletePayment(Long id) throws ApiException {
		em.remove(fetchEntity(id, Payment.class));
	}

	private <E> E fetchEntity(Long id, Class<E> entityClass) throws ApiException {

		E entity = Queries.get(em, entityClass, id);
		if (entity == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid " + entityClass.getSimpleName() + " ID");
		}
		return entity;
	}

	private <E> E fetchEntityOrElse(Long id, Class<E> entityClass, E defaultValue) {
		E entity = Queries.get(em, entityClass, id);
		return entity == null ? defaultValue : entity;
	}
	
}
