package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.components.payment.api.ApiBulkPayment;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.user.UserService;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.payment.BulkPayment;
import com.abelium.inatrace.db.entities.payment.Payment;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderActivityProof;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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

	public ApiPayment getPayment(Long id) throws ApiException {
		return PaymentMapper.toApiPayment(fetchEntity(id, Payment.class));
	}

	public ApiBulkPayment getBulkPayment(Long id) throws ApiException {
		return BulkPaymentMapper.toApiBulkPayment(fetchEntity(id, BulkPayment.class));
	}

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

	public ApiPaginatedList<ApiBulkPayment> listBulkPaymentsByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<BulkPayment> bulkPaymentsQuery =
				em.createNamedQuery("BulkPayment.listBulkPaymentsByCompanyId", BulkPayment.class)
						.setParameter("companyId", companyId)
						.setFirstResult(request.getOffset())
						.setMaxResults(request.getLimit());

		List<BulkPayment> bulkPayments = bulkPaymentsQuery.getResultList();

		Long count =
				em.createNamedQuery("BulkPayment.countBulkPaymentsByCompanyId", Long.class)
						.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
				bulkPayments.stream().map(BulkPaymentMapper::toApiBulkPayment).collect(Collectors.toList()), count);
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

			// Verify document is provided, if payment purpose is FIRST_INSTALLMENT
			if (apiPayment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT && apiPayment.getReceiptDocument() == null)
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Receipt document has to be provided!");

			// Receipt document (note: Storage key needs to be unique)
			if(apiPayment.getReceiptDocument() != null) {
				Document receiptDocument = new Document();
				receiptDocument.setContentType(apiPayment.getReceiptDocument().getContentType());
				receiptDocument.setName(apiPayment.getReceiptDocument().getName());
				receiptDocument.setSize(apiPayment.getReceiptDocument().getSize());
				receiptDocument.setStorageKey(apiPayment.getReceiptDocument().getStorageKey());
				entity.setReceiptDocument(receiptDocument);
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

			// Values from request
			entity.setReceiptDocumentType(apiPayment.getReceiptDocumentType());
			entity.setFormalCreationTime(apiPayment.getFormalCreationTime());
			entity.setPaymentPurposeType(apiPayment.getPaymentPurposeType());
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setPaymentType(apiPayment.getPaymentType());
			entity.setRecipientType(apiPayment.getRecipientType());
			entity.setReceiptNumber(apiPayment.getReceiptNumber());
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
	

	@Transactional
	public ApiBaseEntity createBulkPayment(ApiBulkPayment apiBulkPayment, Long userId) throws ApiException {

		BulkPayment entity = null;

		// If method is POST, let's create a bulk payment from scratch
		// Important to consider that when a bulk payment is created,
		// the purchase order related needs to inherit the activity proofs created within the bulk payment
		entity = new BulkPayment();

		List<Long> stockOrdersIds = apiBulkPayment.getStockOrders();
		StockOrder purchase = null;
		for (Long purchaseId: stockOrdersIds) {

			purchase = stockOrderService.fetchEntity(purchaseId, StockOrder.class);
			if (purchase != null) {

				purchase.setBulkPayment(entity);

                for (ApiActivityProof apiAP : apiBulkPayment.getAdditionalProofs()) {

                    Document activityProofDoc = stockOrderService.fetchEntity(apiAP.getDocument().getId(), Document.class);

                    StockOrderActivityProof stockOrderActivityProof = new StockOrderActivityProof();
                    stockOrderActivityProof.setStockOrder(purchase);
                    stockOrderActivityProof.setActivityProof(new ActivityProof());
                    stockOrderActivityProof.getActivityProof().setDocument(activityProofDoc);
                    stockOrderActivityProof.getActivityProof().setFormalCreationDate(apiAP.getFormalCreationDate());
                    stockOrderActivityProof.getActivityProof().setType(apiAP.getType());
                    stockOrderActivityProof.getActivityProof().setValidUntil(apiAP.getValidUntil());

                    purchase.getActivityProofs().add(stockOrderActivityProof);
                }

				entity.getStockOrders().add(purchase);

			} else {
				throw new ApiException(ApiStatus.INVALID_REQUEST, "A purchase order is required in order to create a bulk payment.");
			}
		}

		entity.setAdditionalCost(apiBulkPayment.getAdditionalCost());
		entity.setAdditionalCostDescription(apiBulkPayment.getAdditionalCostDescription());
		entity.setCreatedBy(userService.fetchUserById(apiBulkPayment.getCreatedBy()));
		entity.setCurrency(apiBulkPayment.getCurrency());
		entity.setFormalCreationTime(Instant.now());
		entity.setPayingCompany(purchase.getCompany());
		entity.setPaymentDescription(apiBulkPayment.getPaymentDescription());
		entity.setPaymentPurposeType(apiBulkPayment.getPaymentPurposeType());
		entity.setReceiptNumber(apiBulkPayment.getReceiptNumber());
		entity.getStockOrders().add(purchase);
		entity.setTotalAmount(apiBulkPayment.getTotalAmount());


		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	public ApiBulkPayment getBulkPayment(Long id) throws ApiException {
		return BulkPaymentMapper.toApiBulkPayment(fetchBulkPayment(id));
	}

	public BulkPayment fetchBulkPayment(Long id) throws ApiException {

		BulkPayment bulkPayment = Queries.get(em, BulkPayment.class, id);
		if (bulkPayment == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid bulk payment ID");
		}
		return bulkPayment;
	}

	public ApiPaginatedList<ApiBulkPayment> listBulkPaymentsByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<BulkPayment> bulkPaymentsQuery =
			em.createNamedQuery("BulkPayment.listBulkPaymentsByCompanyId", BulkPayment.class)
				.setParameter("companyId", companyId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<BulkPayment> bulkPayments = bulkPaymentsQuery.getResultList();

		Long count =
			em.createNamedQuery("BulkPayment.countBulkPaymentsByCompanyId", Long.class)
				.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
				bulkPayments.stream().map(BulkPaymentMapper::toApiBulkPayment).collect(Collectors.toList()), count);
	}

}
