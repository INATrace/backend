package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.payment.PaymentQueryRequest;
import com.abelium.inatrace.components.payment.PaymentService;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.stockorder.StockOrderQueryRequest;
import com.abelium.inatrace.components.stockorder.StockOrderService;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.payment.PaymentStatus;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for csv generators.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Deprecated
@RestController
@RequestMapping("/chain/csv")
public class CommonCsvController {

	private final PaymentService paymentService;
	private final StockOrderService stockOrderService;
	private final CommonCsvService commonCsvService;

	@Autowired
	public CommonCsvController(PaymentService paymentService, StockOrderService stockOrderService, CommonCsvService paymentCsvService) {
		this.paymentService = paymentService;
		this.stockOrderService = stockOrderService;
		this.commonCsvService = paymentCsvService;
	}

	@Deprecated
	@PostMapping(value = "payments/company/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@Operation(summary ="Generate a csv file with a list of filtered payments by companyId.")
	public @ResponseBody byte[] generatePaymentsByCompanyCsv(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid ApiPaginatedRequest request,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @Parameter(description = "Preferred way of payment") @RequestParam(value = "preferredWayOfPayment", required = false) PreferredWayOfPayment preferredWayOfPayment,
			@Valid @Parameter(description = "Payment status") @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus,
			@Valid @Parameter(description = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
			@Valid @Parameter(description = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
			@Valid @Parameter(description = "Search by farmer name") @RequestParam(value = "query", required = false) String farmerName) throws IOException, ApiException {

		request.limit = 500;
		
		ApiPaginatedList<ApiPayment> paginatedPayments = 
			paymentService.getPaymentList(
				request,
				new PaymentQueryRequest(
					companyId,
					null,
					preferredWayOfPayment,
					paymentStatus,
					productionDateStart,
					productionDateEnd,
					farmerName,
					null,
					null
				), 
				authUser
			);
		
		List<ApiPayment> apiPayments = paginatedPayments.getItems();

		return commonCsvService.createPaymentsByCompanyCsv(apiPayments);
	}

	@Deprecated
	@PostMapping(value = "purchases/company/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@Operation(summary ="Generate a csv file with a list of filtered purchases by companyId.")
	public @ResponseBody byte[] generatePurchasesByCompanyCsv(
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid ApiPaginatedRequest request,
			@Valid @Parameter(description = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @Parameter(description = "Preferred way of payment") @RequestParam(value = "preferredWayOfPayment", required = false) PreferredWayOfPayment preferredWayOfPayment,
			@Valid @Parameter(description = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
			@Valid @Parameter(description = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
			@Valid @Parameter(description = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
			@Valid @Parameter(description = "Search by farmer name") @RequestParam(value = "query", required = false) String farmerName) throws IOException, ApiException {

		request.limit = 500;
		
		ApiPaginatedList<ApiStockOrder> paginatedPurchases = 
			stockOrderService.getStockOrderListForCompany(
				request,
				new StockOrderQueryRequest(
					companyId,
					null,
					null,
					null,
					null,
					true,
					null,
					null,
					isWomenShare,
					preferredWayOfPayment,
					null,
					productionDateStart,
					productionDateEnd,
					farmerName
				), 
				authUser,
				language
			);
		
		List<ApiStockOrder> apiPurchases = paginatedPurchases.getItems();

		return commonCsvService.createPurchasesByCompanyCsv(apiPurchases);
	}

}
