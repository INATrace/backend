package com.abelium.inatrace.components.stockorder;


import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        return new ApiResponse<>(stockOrderService.getStockOrder(id));
    }

    @GetMapping("/list")
    @ApiOperation("Get a paginated list of stock orders.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderList(@Valid ApiPaginatedRequest request) {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderList(request));
    }

    @GetMapping("list/facility/{facilityId}")
    @ApiOperation("Get a paginated list of stock orders by Facility ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByFacilityId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("facilityId") Long facilityId) {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListByFacilityId(request, facilityId));
    }

    @GetMapping("list/company/{companyId}")
    @ApiOperation("Get a paginated list of stock orders by company ID.")
    public ApiPaginatedResponse<ApiStockOrder> getStockOrderListByCompanyId(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId) {

        return new ApiPaginatedResponse<>(stockOrderService.getStockOrderListByCompanyId(request, companyId));
    }

    @PutMapping
    @ApiOperation("Create or update stock order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateStockOrder(
            @Valid @RequestBody ApiStockOrder apiStockOrder) throws ApiException {

        return new ApiResponse<>(stockOrderService.createOrUpdateStockOrder(apiStockOrder));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a stock order with the provided ID.")
    public ApiDefaultResponse deleteStockOrder(
            @Valid @ApiParam(value = "StockOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        stockOrderService.deleteStockOrder(id);
        return new ApiDefaultResponse();
    }
}
