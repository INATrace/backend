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
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.types.UserRole;
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

	private final UserService userService;

	@Autowired
	public PaymentService(UserService userService) {
		this.userService = userService;
	}

	public ApiPayment getPayment(Long id, CustomUserDetails user) throws ApiException {
		Payment payment = fetchEntity(id, Payment.class);
		if (
				user.getUserRole() != UserRole.ADMIN &&
				payment.getPayingCompany().getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in payment's company");
		}

		return PaymentMapper.toApiPayment(payment, user.getUserId());
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
		if (queryRequest.representativeOfRecepientUserCustomerId != null) {
			condition = condition.and(paymentProxy.getRepresentativeOfRecipientUserCustomer()).isNotNull();
			condition = condition.and(paymentProxy.getRepresentativeOfRecipientUserCustomer().getId())
					.eq(queryRequest.representativeOfRecepientUserCustomerId);
		}

		Torpedo.where(condition);

		if ("productionDate".equals(request.sortBy)) {
			QueryTools.orderBy(request.sort, paymentProxy.getProductionDate());
		} else {
			QueryTools.orderBy(request.sort, paymentProxy.getId());
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
											   CustomUserDetails user,
											   boolean isPartOfBulkPayment) throws ApiException {

		Payment entity = fetchEntityOrElse(apiPayment.getId(), Payment.class, new Payment());
		User currentUser = userService.fetchUserById(user.getUserId());

		if (entity.getId() != null) {

			// Do not allow update of fields other than the payment status
			// Also it is not allowed to set back the value to UNCONFIRMED
			if (apiPayment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
				if (entity.getPaymentStatus() == PaymentStatus.UNCONFIRMED) {
					entity.setPaymentStatus(PaymentStatus.CONFIRMED);
				}
			}

			entity.setPaymentConfirmedAtTime(Instant.now());
			entity.setPaymentConfirmedByCompany(entity.getPayingCompany());
			entity.setPaymentConfirmedByUser(currentUser);
			entity.setUpdatedBy(currentUser);

		} else {

			if (apiPayment.getRecipientType() == null) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Recipient type is required");
			}

			StockOrder stockOrder = fetchEntity(apiPayment.getStockOrder().getId(), StockOrder.class);
			if(
					user.getUserRole() != UserRole.ADMIN &&
					stockOrder.getCompany().getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
				throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in paying company");
			}

			if (stockOrder.getOrderType() != OrderType.PURCHASE_ORDER && stockOrder.getOrderType() != OrderType.GENERAL_ORDER) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Not a Purchase or Quote order");
			}

			// Verify document is provided, if payment purpose is FIRST_INSTALLMENT
			if (!isPartOfBulkPayment && apiPayment.getPaymentPurposeType() == PaymentPurposeType.FIRST_INSTALLMENT && apiPayment.getReceiptDocument() == null) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Receipt document has to be provided");
			}

			// Verify totalPaid (amount paid to the farmer) is not negative
			if (apiPayment.getAmount().compareTo(BigDecimal.ZERO) < 0) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Total amount paid cannot be negative");
			}

			// Check if recipient type is COMPANY that a recipient Company is provided
			if (apiPayment.getRecipientType() == RecipientType.COMPANY && apiPayment.getRecipientCompany() == null) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Recipient company is required when type is COMPANY");
			}

			// Check if recipient type is USER_CUSTOMER that a recipient user customer is provided
			if (apiPayment.getRecipientType() == RecipientType.USER_CUSTOMER && apiPayment.getRecipientUserCustomer() == null) {
				throw new ApiException(ApiStatus.VALIDATION_ERROR, "Recipient user customer is required when type is USER_CUSTOMER");
			}

			// Receipt document (note: Storage key needs to be unique)
			if (apiPayment.getReceiptDocument() != null) {
				entity.setReceiptDocument(fetchEntity(apiPayment.getReceiptDocument().getId(), Document.class));
			}

			// Values from StockOrder
			entity.setStockOrder(stockOrder);
			entity.setOrderReference(stockOrder.getIdentifier());
			entity.setProductionDate(stockOrder.getProductionDate());
			entity.setPayingCompany(stockOrder.getCompany()); // The company that is paying
			entity.setPurchased(stockOrder.getTotalQuantity());
			entity.setPreferredWayOfPayment(stockOrder.getPreferredWayOfPayment());
			entity.setRecipientUserCustomer(stockOrder.getProducerUserCustomer()); // Farmer
			entity.setRepresentativeOfRecipientUserCustomer(stockOrder.getRepresentativeOfProducerUserCustomer()); // Collector

			// Values from request
			entity.setReceiptDocumentType(apiPayment.getReceiptDocumentType());
			entity.setFormalCreationTime(apiPayment.getFormalCreationTime());
			entity.setPaymentPurposeType(apiPayment.getPaymentPurposeType());
			entity.setPaymentStatus(apiPayment.getPaymentStatus());
			entity.setPaymentType(apiPayment.getPaymentType());
			entity.setRecipientType(apiPayment.getRecipientType());
			entity.setReceiptNumber(apiPayment.getReceiptNumber());
			entity.setCurrency(apiPayment.getCurrency());

			entity.setTotalPaid(apiPayment.getAmount());
			entity.setAmount(apiPayment.getAmount());
			entity.setAmountPaidToTheCollector(BigDecimal.ZERO);

			// If this payment is for Quote order between two companies in the value chain
			if (apiPayment.getRecipientCompany() != null) {
				entity.setRecipientCompany(fetchEntityOrElse(apiPayment.getRecipientCompany().getId(), Company.class, null));
			}

			if (stockOrder.getOrderType() == OrderType.PURCHASE_ORDER && !Boolean.TRUE.equals(stockOrder.getPriceDeterminedLater())) {
				BigDecimal sumTotalPaid = stockOrder.getPayments().stream()
						.map(p -> p.getTotalPaid() != null ? p.getTotalPaid() : BigDecimal.ZERO)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

				stockOrder.setBalance(stockOrder.getCost().subtract(sumTotalPaid).subtract(entity.getTotalPaid() != null ? entity.getTotalPaid() : BigDecimal.ZERO));
				stockOrder.setUpdatedBy(currentUser);
			}

			entity.setCreatedBy(currentUser);
			entity.setUpdatedBy(currentUser);
		}
		
		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}


	@Transactional
	public ApiBaseEntity createBulkPayment(ApiBulkPayment apiBulkPayment, CustomUserDetails user) throws ApiException {

		// Currently bulk-payments cannot be updated...
		if (apiBulkPayment.getId() != null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Bulk payment cannot be updated!");
		}

		// Required fields
		if (apiBulkPayment.getPayingCompany() == null || apiBulkPayment.getPayingCompany().getId() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Paying company ID has to be provided!");
		}

		if (apiBulkPayment.getPayments() == null || apiBulkPayment.getPayments().isEmpty()) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "At least one payment needs to be provided.");
		}

		if (apiBulkPayment.getPaymentDescription() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Payment description needs to be provided.");
		}

		if (apiBulkPayment.getAdditionalCost() != null && apiBulkPayment.getAdditionalCostDescription() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Additional cost description needs to be provided.");
		}

		if (apiBulkPayment.getReceiptNumber() == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Recipient number is required.");
		}

		Company payingCompany = fetchEntity(apiBulkPayment.getPayingCompany().getId(), Company.class);
		if (
				user.getUserRole() != UserRole.ADMIN &&
				payingCompany.getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in paying company");
		}

		BulkPayment entity = new BulkPayment();

		entity.setCreatedBy(userService.fetchUserById(user.getUserId()));
		entity.setPayingCompany(payingCompany);

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

			Long insertedPaymentId = createOrUpdatePayment(apiPayment, user, true).getId();
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
	public void deletePayment(Long id, CustomUserDetails user) throws ApiException {
		Payment payment = fetchEntity(id, Payment.class);
		if (
				user.getUserRole() != UserRole.ADMIN &&
				payment.getPayingCompany().getUsers().stream().noneMatch(cu -> cu.getUser().getId().equals(user.getUserId()))) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "User is not enrolled in payment's company");
		}

		User currentUser = userService.fetchUserById(user.getUserId());
		StockOrder stockOrder = payment.getStockOrder();
		if (stockOrder.getOrderType() == OrderType.PURCHASE_ORDER && !Boolean.TRUE.equals(stockOrder.getPriceDeterminedLater())) {
			BigDecimal sumTotalPaid = stockOrder.getPayments().stream()
					.map(p -> p.getTotalPaid() != null ? p.getTotalPaid() : BigDecimal.ZERO)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			stockOrder.setBalance(stockOrder.getCost().subtract(sumTotalPaid).add(payment.getTotalPaid() != null ? payment.getTotalPaid() : BigDecimal.ZERO));
			stockOrder.setUpdatedBy(currentUser);
		}

		// Remove mapping on both sides, otherwise it does not delete
		stockOrder.getPayments().removeIf(p -> p.getId().equals(payment.getId()));
		em.remove(payment);
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
