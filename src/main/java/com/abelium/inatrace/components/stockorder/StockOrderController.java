package com.abelium.inatrace.components.stockorder;


import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.converters.SimpleDateConverter;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.db.entities.stockorder.enums.PreferredWayOfPayment;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.getStockOrder(id, authUser.getUserId()));
    }

    @GetMapping("/list")
    @ApiOperation("Get a paginated list of stock orders.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderList(
            @Valid ApiPaginatedRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request, new StockOrderQueryRequest(), authUser.getUserId()));
    }

    @GetMapping("listAvailableStockForSemiProductInFacility")
    @ApiOperation("Get a paginated list of stock orders for provided semi product ID and facility ID.")
    public ApiPaginatedResponse<ApiStockOrder> getAvailableStockForSemiProductInFacility(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Facility ID", required = true) @RequestParam("facilityId") Long facilityId,
            @Valid @ApiParam(value = "SemiProduct ID", required = true) @RequestParam("semiProductId") Long semiProductId,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @AuthenticationPrincipal CustomUserDetails authUser) {

        // TODO: Should company be verified (if facility is part of user's company)

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request,
                new StockOrderQueryRequest(
                        facilityId,
                        semiProductId,
                        true,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null
                ),
                authUser.getUserId()));
    }

    @GetMapping("list/facility/{facilityId}")
    @ApiOperation("Get a paginated list of stock orders by facility ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByFacilityId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("facilityId") Long facilityId,
            @Valid @ApiParam(value = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @Valid @ApiParam(value = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
            @AuthenticationPrincipal CustomUserDetails authUser) {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request,
                new StockOrderQueryRequest(
                        null,
                        facilityId,
                        null,
                        isOpenBalanceOnly,
                        isWomenShare,
                        wayOfPayment,
                        null,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null,
                        producerUserCustomerName
                ),
                authUser.getUserId()));
    }

    @GetMapping("list/company/{companyId}")
    @ApiOperation("Get a paginated list of stock orders by company ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByCompanyId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @ApiParam(value = "Farmer (UserCustomer) ID") @RequestParam(value = "farmerId", required = false) Long farmerId,
            @Valid @ApiParam(value = "Is open balance only") @RequestParam(value = "isOpenBalanceOnly", required = false) Boolean isOpenBalanceOnly,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Way of payment") @RequestParam(value = "wayOfPayment", required = false) PreferredWayOfPayment wayOfPayment,
            @Valid @ApiParam(value = "Order type") @RequestParam(value = "orderType", required = false) OrderType orderType,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) @DateTimeFormat(pattern = SimpleDateConverter.SIMPLE_DATE_FORMAT) Date productionDateEnd,
            @Valid @ApiParam(value = "Search by ProducerUserCustomer name") @RequestParam(value = "query", required = false) String producerUserCustomerName,
            @AuthenticationPrincipal CustomUserDetails authUser

    ) {
        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(
                request,
                new StockOrderQueryRequest(
                        companyId,
                        null,
                        farmerId,
                        isOpenBalanceOnly,
                        isWomenShare,
                        wayOfPayment,
                        orderType,
                        productionDateStart != null ? productionDateStart.toInstant() : null,
                        productionDateEnd != null ? productionDateEnd.toInstant() : null,
                        producerUserCustomerName
                ),
                authUser.getUserId()));
    }

    @PutMapping
    @ApiOperation("Create or update stock order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateStockOrder(
            @Valid @RequestBody ApiStockOrder apiStockOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(stockOrderService.createOrUpdateStockOrder(apiStockOrder, authUser.getUserId()));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a stock order with the provided ID.")
    public ApiDefaultResponse deleteStockOrder(
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        stockOrderService.deleteStockOrder(id);
        return new ApiDefaultResponse();
    }
}
