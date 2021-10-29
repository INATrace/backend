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
import com.abelium.inatrace.db.entities.payment.*;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

	public ApiPayment getPayment(Long id, Long userId) throws ApiException {
		return PaymentMapper.toApiPayment(fetchEntity(id, Payment.class), userId);
	}

	public ApiBulkPayment getBulkPayment(Long id, Long userId) throws ApiException {
		return BulkPaymentMapper.toApiBulkPayment(fetchEntity(id, BulkPayment.class), userId);
	}

	public ApiPaginatedList<ApiPayment> getPaymentList(ApiPaginatedRequest request, PaymentQueryRequest queryRequest, Long userId) {
		return PaginationTools.createPaginatedResponse(em, request, () -> paymentQueryObject(
				request, queryRequest), payment -> PaymentMapper.toApiPayment(payment, userId));
	}

	private Payment paymentQueryObject(ApiPaginatedRequest request, PaymentQueryRequest queryRequest) {

		Payment paymentProxy = Torpedo.from(Payment.class);
		OnGoingLogicalCondition condition = Torpedo.condition();

		// Applies only when fetching list by PurchaseID or CompanyID
		if (queryRequest.companyId != null) {
			condition = condition.and(paymentProxy.getStockOrder().getCompany().getId()).eq(queryRequest.companyId);
		} else if (queryRequest.purchaseId != null) {
			condition = condition.and(paymentProxy.getStockOrder().getId()).eq(queryRequest.purchaseId);
		}

		// Query parameter filters
		if(queryRequest.paymentStatus != null) {
			condition = condition.and(paymentProxy.getPaymentStatus()).eq(queryRequest.paymentStatus);
		}
		if(queryRequest.preferredWayOfPayment != null) {
			condition = condition.and(paymentProxy.getPreferredWayOfPayment()).eq(queryRequest.preferredWayOfPayment);
		}
		if(queryRequest.productionDateStart != null) {
			condition = condition.and(paymentProxy.getProductionDate()).gte(queryRequest.productionDateStart);
		}
		if(queryRequest.productionDateEnd != null) {
			condition = condition.and(paymentProxy.getProductionDate()).lte(queryRequest.productionDateEnd);
		}
		if(queryRequest.farmerName != null) { // Search by farmers name (query)
			condition = condition.and(paymentProxy.getRecipientUserCustomer()).isNotNull();
			OnGoingLogicalCondition likeName = Torpedo.condition(paymentProxy.getRecipientUserCustomer().getName()).like().any(queryRequest.farmerName);
			OnGoingLogicalCondition likeSurname = Torpedo.condition(paymentProxy.getRecipientUserCustomer().getSurname()).like().any(queryRequest.farmerName);
			condition = condition.and(Torpedo.condition(likeName.or(likeSurname)));
		}
		if (queryRequest.farmerId != null) {
			condition = condition.and(paymentProxy.getRecipientUserCustomer()).isNotNull();
			condition = condition.and(paymentProxy.getRecipientUserCustomer().getId()).eq(queryRequest.farmerId);
		}

		Torpedo.where(condition);

		switch (request.sortBy) {
			case "productionDate": QueryTools.orderBy(request.sort, paymentProxy.getProductionDate()); break;
			default: QueryTools.orderBy(request.sort, paymentProxy.getId());
		}

		return paymentProxy;
	}

	public ApiPaginatedList<ApiBulkPayment> listBulkPayments(ApiPaginatedRequest request,
															 PaymentQueryRequest queryRequest,
															 Long userId) {

		return PaginationTools.createPaginatedResponse(em, request, () -> bulkPaymentQueryObject(
				request, queryRequest), bulkPayment -> BulkPaymentMapper.toApiBulkPaymentBase(bulkPayment, userId));
	}

	private BulkPayment bulkPaymentQueryObject(ApiPaginatedRequest request, PaymentQueryRequest queryRequest) {

		BulkPayment bulkPaymentProxy = Torpedo.from(BulkPayment.class);
		OnGoingLogicalCondition condition = Torpedo.condition();

		if (queryRequest.companyId != null) {
			condition = condition.and(bulkPaymentProxy.getPayingCompany().getId()).eq(queryRequest.companyId);
		}

		Torpedo.where(condition);

		switch (request.sortBy) {
			case "receiptNumber": QueryTools.orderBy(request.sort, bulkPaymentProxy.getReceiptNumber()); break;
			case "totalAmount": QueryTools.orderBy(request.sort, bulkPaymentProxy.getTotalAmount()); break;
			case "paymentPurposeType": QueryTools.orderBy(request.sort, bulkPaymentProxy.getPaymentPurposeType()); break;
			default: QueryTools.orderBy(request.sort, bulkPaymentProxy.getId());
		}

		return bulkPaymentProxy;
	}

	@Transactional
	public ApiBaseEntity createOrUpdatePayment(ApiPayment apiPayment,
											   Long userId,
											   boolean isPartOfBulkPayment) throws ApiException {

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

			StockOrder stockOrder = fetchEntity(apiPayment.getStockOrder().getId(), StockOrder.class);;
			if (stockOrder.getOrderType() != OrderType.PURCHASE_ORDER) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Not a purchase order");
			}

			// Verify document is provided, if payment purpose is FIRST_INSTALLMENT
			if (!isPartOfBulkPayment && apiPayment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT && apiPayment.getReceiptDocument() == null)
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Receipt document has to be provided!");

			// Verify totalPaid (amount paid to the farmer) is not negative
			if (apiPayment.getAmountPaidToTheFarmer().compareTo(BigDecimal.ZERO) < 0)
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total amount paid cannot be negative");

			// Receipt document (note: Storage key needs to be unique)
			if(apiPayment.getReceiptDocument() != null) {
				entity.setReceiptDocument(fetchEntity(apiPayment.getReceiptDocument().getId(), Document.class));
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
			entity.setAmountPaidToTheCollector(BigDecimal.ZERO); // ?

			// TODO: Optional ? -> Or should be error thrown?
			if (apiPayment.getRecipientCompany() != null)
				entity.setRecipientCompany(fetchEntityOrElse(apiPayment.getRecipientCompany().getId(), Company.class, null));
			if (apiPayment.getRepresentativeOfRecipientCompany() != null)
				entity.setRecipientCompany(fetchEntityOrElse(apiPayment.getRepresentativeOfRecipientCompany().getId(), Company.class, null));

			entity.setCreatedBy(currentUser);
			entity.setUpdatedBy(currentUser);

			stockOrder.setBalance(stockOrder.getBalance().subtract(entity.getTotalPaid()));
			stockOrder.setUpdatedBy(currentUser);
		}
		
		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}


	@Transactional
	public ApiBaseEntity createBulkPayment(ApiBulkPayment apiBulkPayment, Long userId) throws ApiException {

		// Currently bulk-payments cannot be updated...
		if (apiBulkPayment.getId() != null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Bulk payment cannot be updated!");
		}

		// Required fields
		if(apiBulkPayment.getPayingCompany() == null || apiBulkPayment.getPayingCompany().getId() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Paying company ID has to be provided!");
		}
		if(apiBulkPayment.getPayments() == null || apiBulkPayment.getPayments().isEmpty()) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one payment needs to be provided.");
		}
		if(apiBulkPayment.getPaymentDescription() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Payment description needs to be provided.");
		}
		if(apiBulkPayment.getAdditionalCost() != null && apiBulkPayment.getAdditionalCostDescription() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Additional cost description needs to be provided.");
		}
		if(apiBulkPayment.getReceiptNumber() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Recipient number is required.");
		}

		BulkPayment entity = new BulkPayment();

		entity.setCreatedBy(userService.fetchUserById(userId));
		entity.setPayingCompany(fetchEntity(apiBulkPayment.getPayingCompany().getId(), Company.class));

		entity.setPaymentDescription(apiBulkPayment.getPaymentDescription());
		entity.setPaymentPurposeType(apiBulkPayment.getPaymentPurposeType());
		entity.setReceiptNumber(apiBulkPayment.getReceiptNumber());
		entity.setTotalAmount(apiBulkPayment.getTotalAmount());
		entity.setAdditionalCost(apiBulkPayment.getAdditionalCost());
		entity.setAdditionalCostDescription(apiBulkPayment.getAdditionalCostDescription());
		entity.setCurrency(apiBulkPayment.getCurrency());
		entity.setFormalCreationTime(apiBulkPayment.getFormalCreationTime() != null
				? apiBulkPayment.getFormalCreationTime()
				: Instant.now());

		// Create payments
		for (ApiPayment apiPayment: apiBulkPayment.getPayments()) {

			Long insertedPaymentId = createOrUpdatePayment(apiPayment, userId, true).getId();
			Payment payment = fetchEntity(insertedPaymentId, Payment.class);

			// Bi-directional mapping
			payment.setBulkPayment(entity);
			entity.getPayments().add(payment);
		}

		// Add activity proofs
		for (ApiActivityProof apiActivityProof : apiBulkPayment.getAdditionalProofs()) {

			Document activityProofDoc = fetchEntity(apiActivityProof.getDocument().getId(), Document.class);

			BulkPaymentActivityProof bulkPaymentActivityProof = new BulkPaymentActivityProof();
			bulkPaymentActivityProof.setBulkPayment(entity);
			bulkPaymentActivityProof.setActivityProof(new ActivityProof());
			bulkPaymentActivityProof.getActivityProof().setDocument(activityProofDoc);
			bulkPaymentActivityProof.getActivityProof().setType(apiActivityProof.getType());
			bulkPaymentActivityProof.getActivityProof().setFormalCreationDate(apiActivityProof.getFormalCreationDate());
			bulkPaymentActivityProof.getActivityProof().setValidUntil(apiActivityProof.getValidUntil());

			entity.getAdditionalProofs().add(bulkPaymentActivityProof);
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

	@Transactional
	public void deleteBulkPayment(Long id) throws ApiException {
		em.remove(fetchEntity(id, BulkPayment.class));
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
