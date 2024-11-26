package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST resource for Transaction entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/transaction")
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping("list/input/stock-order/{stockOrderId}")
	@Operation(summary = "Get a paginated list of input transactions for provided stock order ID.")
	public ApiPaginatedResponse<ApiTransaction> getStockOrderInputTransactions(
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("stockOrderId") Long stockOrderId,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiPaginatedResponse<>(transactionService.getStockOrderInputTransactions(stockOrderId, authUser, language));
	}

	@PutMapping("/{id}/approve")
	@Operation(summary = "Approves transaction with provided ID.")
	public ApiDefaultResponse approveTransaction(
			@Valid @Parameter(description = "Transaction ID", required = true) @PathVariable("id") Long id,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		transactionService.approveTransaction(id, authUser, language);
		return new ApiDefaultResponse();
	}

	@PutMapping("/{id}/reject")
	@Operation(summary = "Rejects transaction with provided ID and reverts it's quantities.")
	public ApiDefaultResponse rejectTransaction(
			@Valid @Parameter(description = "Transaction ID", required = true) @PathVariable("id") Long id,
			@Valid @RequestBody ApiTransaction apiTransaction,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		if (apiTransaction == null || !id.equals(apiTransaction.getId())) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Data integrity violation!");
		}

		transactionService.rejectTransaction(apiTransaction, authUser, language);
		return new ApiDefaultResponse();
	}

}
