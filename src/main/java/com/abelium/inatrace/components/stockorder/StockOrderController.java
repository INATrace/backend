package com.abelium.inatrace.components.stockorder;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.components.stockorder.api.ApiPurchaseOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderHistory;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/chain/stock-order")
public class StockOrderController {

    private final StockOrderService stockOrderService;

    @Autowired
    public StockOrderController(StockOrderService stockOrderService) {
        this.stockOrderService = stockOrderService;
    }

    @GetMapping("{id}")
    @Operation(summary ="Get a single stock order with the provided ID.")
    public ApiResponse<ApiStockOrder> getStockOrder(
            @Valid @Parameter(name = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @Valid @Parameter(name = "Return the processing order base data") @RequestParam(value = "withProcessingOrder", required = false) Boolean withProcessingOrder,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiResponse<>(stockOrderService.getStockOrder(id, authUser, language, withProcessingOrder));
    }

    @GetMapping("{id}/processing-order")
    @Operation(summary ="Get the Processing order that contains the Stock order with the provided ID.")
    public ApiResponse<ApiProcessingOrder> getStockOrderProcessingOrder(
            @Valid @Parameter(name = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) throws ApiException {

        return new ApiResponse<>(stockOrderService.getStockOrderProcessingOrder(id, authUser, language));
    }

    @GetMapping("/list/facility/{facilityId}/available")
    @Operation(summary ="Get a paginated list of stock orders for provided facility ID and semi-product or final product ID.")
    public ApiPaginatedResponse<ApiStockOrder> getAvailableStockForStockUnitInFacility(
            @Valid ApiPaginatedRequest request,
            @Valid @Parameter(name = "Facility ID", required = true) @PathVariable("facilityId") Long facilityId,
            @Valid @Parameter(name = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @Parameter(name = "Final product ID") @RequestParam(value = "finalProductId", required = false) Long finalProductId,
            @Valid @Parameter(name = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @Parameter(name = "Organic only") @RequestParam(value = "organicOnly", required = false) Boolean organicOnly,
            @Valid @Parameter(name = "Internal LOT name") @RequestParam(value = "internalLotName", required = false) String internalLotName,
            @Valid @Parameter(name = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
            @Valid @Parameter(name = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiPaginatedResponse<>(stockOrderService.getAvailableStockOrderListForFacility(
                request,
                new StockOrderQueryRequest(
                        facilityId,
                        semiProductId,
                        finalProductId,
                        true,
                        isWomenShare,
                        organicOnly,
                        internalLotName,
                        productionDateStart,
                        productionDateEnd
                ),
                authUser,
                language));
    }

    @GetMapping("list/facility/{facilityId}")
    @Operation(summary ="Get a paginated list of stock orders by facility ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByFacilityId(
            @Valid ApiPaginatedRequest request,
            @Valid @Parameter(name = "Facility ID", required = true) @PathVariable("facilityId") Long facilityId,
            @Valid @Parameter(name = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @Parameter(name = "Is purchase orders only") @RequestParam(value = "isPurchaseOrderOnly", required = false) Boolean isPurchaseOrderOnly,
            @Valid @Parameter(name = "Available orders only") @RequestParam(value = "availableOnly", required = false) Boolean availableOnly,
            @Valid @Parameter(name = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @Parameter(name = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @Parameter(name = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @Parameter(name = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
            @Valid @Parameter(name = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
            @Valid @Parameter(name = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
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
                        productionDateStart,
                        productionDateEnd,
                        producerUserCustomerName
                ),
                authUser,
                language));
    }

    @GetMapping("list/company/{companyId}/orders-for-customers")
    @Operation(summary ="Get a paginated list of stock orders by facility ID for customers.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrdersInFacilityForCustomer(
            @Valid ApiPaginatedRequest request,
            @Valid @Parameter(name = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @Parameter(name = "Facility ID") @RequestParam(value = "facilityId", required = false) Long facilityId,
            @Valid @Parameter(name = "Company customer ID") @RequestParam(value = "companyCustomerId", required = false) Long companyCustomerId,
            @Valid @Parameter(name = "Return only open stock orders") @RequestParam(value = "openOnly", required = false) Boolean openOnly,
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
            @Valid @Parameter(name = "Quote company ID", required = true) @PathVariable("companyId") Long quoteCompanyId,
            @Valid @Parameter(name = "Quote facility ID") @RequestParam(value = "facilityId", required = false) Long quoteFacilityId,
            @Valid @Parameter(name = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @Parameter(name = "Return only open stock orders") @RequestParam(value = "openOnly", required = false) Boolean openOnly,
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
    @Operation(summary ="Get a paginated list of stock orders by company ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByCompanyId(
            @Valid ApiPaginatedRequest request,
            @Valid @Parameter(name = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @Parameter(name = "Farmer (UserCustomer) ID") @RequestParam(value = "farmerId", required = false) Long farmerId,
            @Valid @Parameter(name = "Representative of farmer (UserCustomer) ID") @RequestParam(value = "representativeOfProducerUserCustomerId", required = false) Long representativeOfProducerUserCustomerId,
            @Valid @Parameter(name = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @Parameter(name = "Is purchase orders only") @RequestParam(value = "isPurchaseOrderOnly", required = false) Boolean isPurchaseOrderOnly,
            @Valid @Parameter(name = "Available orders only") @RequestParam(value = "availableOnly", required = false) Boolean availableOnly,
            @Valid @Parameter(name = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @Parameter(name = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @Parameter(name = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @Parameter(name = "Order type") @RequestParam(value = "orderType", required = false) OrderType orderType,
            @Valid @Parameter(name = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
            @Valid @Parameter(name = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
            @Valid @Parameter(name = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
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
                        productionDateStart,
                        productionDateEnd,
                        producerUserCustomerName
                ),
                authUser,
                language));
    }

    @PostMapping("bulk-purchase")
    @Operation(summary ="Creates a list of purchase orders.")
    public ApiResponse<ApiPurchaseOrder> createPurchaseOrderBulk(
            @Valid @RequestBody ApiPurchaseOrder apiPurchaseOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.createPurchaseBulkOrder(apiPurchaseOrder, authUser));
    }

    @PutMapping
    @Operation(summary ="Create or update stock order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateStockOrder(
            @Valid @RequestBody ApiStockOrder apiStockOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.createOrUpdateStockOrder(apiStockOrder, authUser, null));
    }

    @DeleteMapping("{id}")
    @Operation(summary ="Deletes a stock order with the provided ID.")
    public ApiDefaultResponse deleteStockOrder(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @Parameter(name = "StockOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        stockOrderService.deleteStockOrder(id, authUser);
        return new ApiDefaultResponse();
    }

    @GetMapping("{id}/aggregated-history")
    public ApiResponse<ApiStockOrderHistory> getStockOrderAggregatedHistory(
            @Valid @Parameter(name = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) throws ApiException {
        return new ApiResponse<>(stockOrderService.getStockOrderAggregatedHistoryList(id, language, authUser, true));
    }

    @GetMapping(value = "{id}/exportGeoData", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary ="Generate a geoJSON file with a list of polygons.")
    public @ResponseBody byte[] exportGeoData(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @Parameter(name = "StockOrder ID", required = true) @PathVariable("id") Long id,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        ApiStockOrderHistory apiStockOrderHistory =
                stockOrderService.getStockOrderAggregatedHistoryList(id, language, authUser, true);

        return stockOrderService.createGeoJsonFromDeliveries(apiStockOrderHistory.getTimelineItems());
    }

    @GetMapping(value = "export/deliveries/company/{companyId}")
    @Operation(summary ="Export deliveries (stock orders of type PURCHASE_ORDER) for the provided company ID")
    public ResponseEntity<byte[]> exportDeliveriesByCompany(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @Parameter(name = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        byte[] response;
        try {
            response = stockOrderService.exportDeliveriesByCompany(authUser, companyId, language);
        } catch (IOException e) {
            throw new ApiException(ApiStatus.ERROR, "Error while exporting file!");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }

}
