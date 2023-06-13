package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	@ApiOperation("Get a paginated list of input transactions for provided stock order ID.")
	public ApiPaginatedResponse<ApiTransaction> getStockOrderInputTransactions(
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("stockOrderId") Long stockOrderId,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiPaginatedResponse<>(transactionService.getStockOrderInputTransactions(stockOrderId, authUser, language));
	}

	@PutMapping("/{id}/approve")
	@ApiOperation("Approves transaction with provided ID.")
	public ApiDefaultResponse approveTransaction(
			@Valid @ApiParam(value = "Transaction ID", required = true) @PathVariable("id") Long id,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		transactionService.approveTransaction(id, authUser, language);
		return new ApiDefaultResponse();
	}

	@PutMapping("/{id}/reject")
	@ApiOperation("Rejects transaction with provided ID and reverts it's quantities.")
	public ApiDefaultResponse rejectTransaction(
			@Valid @ApiParam(value = "Transaction ID", required = true) @PathVariable("id") Long id,
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
