package com.abelium.inatrace.components.transaction;

import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("stockOrderId") Long stockOrderId) throws ApiException {

		return new ApiPaginatedResponse<>(transactionService.getStockOrderInputTransactions(stockOrderId));
	}

}
