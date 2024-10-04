package com.abelium.inatrace.components.payment;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.payment.api.ApiBulkPayment;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

/**
 * REST controller for payment entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/payment")
public class PaymentController {

	private final PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@GetMapping("{id}")
	@Operation(summary = "Get a single payment with the provided ID.")
	public ApiResponse<ApiPayment> getPayment(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @Parameter(description = "Payment ID", required = true) @PathVariable("id") Long id) throws ApiException {
		return new ApiResponse<>(paymentService.getPayment(id, authUser));
	}
	
	@GetMapping("bulk-payment/{id}")
	@Operation(summary = "Get a single bulk payment with the provided ID.")
	public ApiResponse<ApiBulkPayment> getBulkPayment(
			@Valid @Parameter(description = "Bulk payment ID", required = true) @PathVariable("id") Long id,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiResponse<>(paymentService.getBulkPayment(id, authUser));
	}

	@GetMapping("list/purchase/{id}")
	@Operation(summary = "Get a list of payments by purchase order (stock order) ID.")
	public ApiPaginatedResponse<ApiPayment> listPaymentsByPurchase(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid ApiPaginatedRequest request,
			@Valid @Parameter(description = "Purchase ID", required = true) @PathVariable("id") Long purchaseId,
			@Valid @Parameter(description = "Preferred way of payment") @RequestParam(value = "preferredWayOfPayment", required = false) PreferredWayOfPayment preferredWayOfPayment,
			@Valid @Parameter(description = "Payment status") @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus,
			@Valid @Parameter(description = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
			@Valid @Parameter(description = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
			@Valid @Parameter(description = "Search by farmer name") @RequestParam(value = "query", required = false) String farmerName) throws ApiException {

		return new ApiPaginatedResponse<>(paymentService.getPaymentList(
				request,
				new PaymentQueryRequest(
						null,
						purchaseId,
						preferredWayOfPayment,
						paymentStatus,
						productionDateStart,
						productionDateEnd,
						farmerName,
						null,
						null
				),
				authUser
		));
	}

	@GetMapping("list/company/{id}")
	@Operation(summary = "Get a list of payments by company ID.")
	public ApiPaginatedResponse<ApiPayment> listPaymentsByCompany(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid ApiPaginatedRequest request,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @Parameter(description = "Preferred way of payment") @RequestParam(value = "preferredWayOfPayment", required = false) PreferredWayOfPayment preferredWayOfPayment,
			@Valid @Parameter(description = "Payment status") @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus,
			@Valid @Parameter(description = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
			@Valid @Parameter(description = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
			@Valid @Parameter(description = "Search by farmer name") @RequestParam(value = "query", required = false) String farmerName,
			@Valid @Parameter(description = "Search by farmer id") @RequestParam(value = "farmerId", required = false) Long farmerId,
			@Valid @Parameter(description = "Search by representative of farmer id") @RequestParam(value = "representativeOfRecipientUserCustomerId", required = false) Long representativeOfRecipientUserCustomerId) throws ApiException {

		return new ApiPaginatedResponse<>(paymentService.getPaymentList(
				request,
				new PaymentQueryRequest(
						companyId,
						null,
						preferredWayOfPayment,
						paymentStatus,
						productionDateStart,
						productionDateEnd,
						farmerName,
						farmerId,
						representativeOfRecipientUserCustomerId
				), authUser
		));
	}

	@GetMapping(value = "export/company/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@Operation(summary = "Export payments for provided company ID")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					content = @Content(schema = @Schema(type = "string", format = "binary"))
			)
	})
	public ResponseEntity<byte[]> exportPaymentsByCompany(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
	) throws ApiException {

		byte[] response;
		try {
			response = paymentService.exportPaymentsByCompany(authUser, companyId, language);
		} catch (IOException e) {
			throw new ApiException(ApiStatus.ERROR, "Error while exporting file!");
		}

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(response);
	}

	@GetMapping("list/bulk-payment/company/{id}")
	@Operation(summary = "Get a list of bulk payments by company ID.")
	public ApiPaginatedResponse<ApiBulkPayment> listBulkPaymentsByCompany(
		@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
		@AuthenticationPrincipal CustomUserDetails authUser,
		@Valid ApiPaginatedRequest request) throws ApiException {

		return new ApiPaginatedResponse<>(paymentService.listBulkPayments(
				request,
				new PaymentQueryRequest(companyId),
				authUser)
		);
	}

	@GetMapping(value = "export/bulk-payment/company/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@Operation(summary = "Export bulk-payments for provided company ID")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					content = @Content(schema = @Schema(type = "string", format = "binary"))
			)
	})
	public ResponseEntity<byte[]> exportBulkPaymentsByCompany(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
	) throws ApiException {

		byte[] response;
		try {
			response = paymentService.exportBulkPaymentsByCompany(authUser, companyId, language);
		} catch (IOException e) {
			throw new ApiException(ApiStatus.ERROR, "Error while exporting file!");
		}

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(response);
	}

	@PutMapping
	@Operation(
			summary = "Create or update payment. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdatePayment(
			@Valid @RequestBody ApiPayment apiPayment,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiResponse<>(paymentService.createOrUpdatePayment(apiPayment, authUser, false));
	}
	
	@PostMapping("bulk-payment")
	@Operation(summary = "Create bulk payment.")
	public ApiResponse<ApiBaseEntity> createBulkPayment(
			@Valid @RequestBody ApiBulkPayment apiBulkPayment,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		return new ApiResponse<>(paymentService.createBulkPayment(apiBulkPayment, authUser));
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Deletes a payment with the provided ID.")
	public ApiDefaultResponse deletePayment(
			@Valid @Parameter(description = "Payment ID", required = true) @PathVariable("id") Long id,
			@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

		paymentService.deletePayment(id, authUser);
		return new ApiDefaultResponse();
	}

}
