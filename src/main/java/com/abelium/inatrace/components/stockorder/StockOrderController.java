package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.stockorder.api.ApiPurchaseOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderHistory;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/chain/stock-order")
public class StockOrderController {

    private final StockOrderService stockOrderService;

    @Autowired
    public StockOrderController(StockOrderService stockOrderService) {
        this.stockOrderService = stockOrderService;
    }

    @GetMapping("{id}")
    @ApiOperation("Get a single stock order with the provided ID.")
    public ApiResponse<ApiStockOrder> getStockOrder(
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @Valid @ApiParam(value = "Return the processing order base data") @RequestParam(value = "withProcessingOrder", required = false) Boolean withProcessingOrder,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiResponse<>(stockOrderService.getStockOrder(id, authUser.getUserId(), language, withProcessingOrder));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation("Get a paginated list of stock orders.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderList(
            @Valid ApiPaginatedRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {
        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request, new StockOrderQueryRequest(), authUser.getUserId(), language));
    }

    @GetMapping("/list/facility/{facilityId}/available")
    @ApiOperation("Get a paginated list of stock orders for provided facility ID and semi-product or final product ID.")
    public ApiPaginatedResponse<ApiStockOrder> getAvailableStockForStockUnitInFacility(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("facilityId") Long facilityId,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Final product ID") @RequestParam(value = "finalProductId", required = false) Long finalProductId,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Organic only") @RequestParam(value = "organicOnly", required = false) Boolean organicOnly,
            @Valid @ApiParam(value = "Internal LOT name") @RequestParam(value = "internalLotName", required = false) String internalLotName,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request,
                new StockOrderQueryRequest(
                        facilityId,
                        semiProductId,
                        finalProductId,
                        true,
                        isWomenShare,
                        organicOnly,
                        internalLotName,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null
                ),
                authUser.getUserId(),
                language));
    }

    @GetMapping("list/facility/{facilityId}")
    @ApiOperation("Get a paginated list of stock orders by facility ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByFacilityId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("facilityId") Long facilityId,
            @Valid @ApiParam(value = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @ApiParam(value = "Is purchase orders only") @RequestParam(value = "isPurchaseOrderOnly", required = false) Boolean isPurchaseOrderOnly,
            @Valid @ApiParam(value = "Available orders only") @RequestParam(value = "availableOnly", required = false) Boolean availableOnly,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @Valid @ApiParam(value = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListForCompany(
                request,
                new StockOrderQueryRequest(
                        null,
                        facilityId,
                        null,
                        null,
                        isOpenBalanceOnly,
                        isPurchaseOrderOnly,
                        availableOnly,
                        semiProductId,
                        isWomenShare,
                        wayOfPayment,
                        null,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null,
                        producerUserCustomerName
                ),
                authUser,
                language));
    }

    @GetMapping("list/company/{companyId}/orders-for-customers")
    @ApiOperation("Get a paginated list of stock orders by facility ID for customers.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrdersInFacilityForCustomer(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @ApiParam(value = "Facility ID") @RequestParam(value = "facilityId", required = false) Long facilityId,
            @Valid @ApiParam(value = "Company customer ID") @RequestParam(value = "companyCustomerId", required = false) Long companyCustomerId,
            @Valid @ApiParam(value = "Return only open stock orders") @RequestParam(value = "openOnly", required = false) Boolean openOnly,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListForCompany(request,
                new StockOrderQueryRequest(
                        companyId,
                        facilityId,
                        null,
                        null,
                        null,
                        companyCustomerId,
                        openOnly
                ), authUser, language));
    }

    @GetMapping("list/company/{companyId}/quote-orders")
    public ApiPaginatedResponse<ApiStockOrder> getQuoteOrdersInFacility(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Quote company ID", required = true) @PathVariable("companyId") Long quoteCompanyId,
            @Valid @ApiParam(value = "Quote facility ID") @RequestParam(value = "facilityId", required = false) Long quoteFacilityId,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Return only open stock orders") @RequestParam(value = "openOnly", required = false) Boolean openOnly,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListForCompany(request,
                new StockOrderQueryRequest(
                        null,
                        null,
                        quoteCompanyId,
                        quoteFacilityId,
                        semiProductId,
                        null,
                        openOnly
                ), authUser, language));
    }

    @GetMapping("list/company/{companyId}")
    @ApiOperation("Get a paginated list of stock orders by company ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByCompanyId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @ApiParam(value = "Farmer (UserCustomer) ID") @RequestParam(value = "farmerId", required = false) Long farmerId,
            @Valid @ApiParam(value = "Representative of farmer (UserCustomer) ID") @RequestParam(value = "representativeOfProducerUserCustomerId", required = false) Long representativeOfProducerUserCustomerId,
            @Valid @ApiParam(value = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @ApiParam(value = "Is purchase orders only") @RequestParam(value = "isPurchaseOrderOnly", required = false) Boolean isPurchaseOrderOnly,
            @Valid @ApiParam(value = "Available orders only") @RequestParam(value = "availableOnly", required = false) Boolean availableOnly,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @ApiParam(value = "Order type") @RequestParam(value = "orderType", required = false) OrderType orderType,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @Valid @ApiParam(value = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
            @AuthenticationPrincipal CustomUserDetails authUser,
        @RequestHeader(value = "language" ,defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListForCompany(
                request,
                new StockOrderQueryRequest(
                        companyId,
                        null,
                        farmerId,
                        representativeOfProducerUserCustomerId,
                        isOpenBalanceOnly,
                        isPurchaseOrderOnly,
                        availableOnly,
                        semiProductId,
                        isWomenShare,
                        wayOfPayment,
                        orderType,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null,
                        producerUserCustomerName
                ),
                authUser,
                language));
    }

    @PostMapping("bulk-purchase")
    @ApiOperation("Creates a list of purchase orders.")
    public ApiResponse<ApiPurchaseOrder> createPurchaseOrderBulk(
            @Valid @RequestBody ApiPurchaseOrder apiPurchaseOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.createPurchaseBulkOrder(apiPurchaseOrder, authUser));
    }

    @PutMapping
    @ApiOperation("Create or update stock order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateStockOrder(
            @Valid @RequestBody ApiStockOrder apiStockOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.createOrUpdateStockOrder(apiStockOrder, authUser, null));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a stock order with the provided ID.")
    public ApiDefaultResponse deleteStockOrder(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        stockOrderService.deleteStockOrder(id, authUser);
        return new ApiDefaultResponse();
    }

    @GetMapping("{id}/aggregated-history")
    public ApiResponse<ApiStockOrderHistory> getStockOrderAggregatedHistory(
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) throws ApiException {
        return new ApiResponse<>(stockOrderService.getStockOrderAggregatedHistoryList(id, language, true));
    }

}
