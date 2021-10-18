package com.abelium.inatrace.components.processingorder;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("chain/processing-order")
public class ProcessingOrderController {

    private final ProcessingOrderService processingOrderService;

    @Autowired
    public ProcessingOrderController(ProcessingOrderService processingOrderService) {
        this.processingOrderService = processingOrderService;
    }

    @GetMapping("{id}")
    @ApiOperation("Get a single processing order with the provided ID.")
    public ApiResponse<ApiProcessingOrder> getProcessingOrder(
            @Valid @ApiParam(value = "ProcessingOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        return new ApiResponse<>(processingOrderService.getProcessingOrder(id));
    }

    @GetMapping("/list")
    @ApiOperation("Get a paginated list of processing orders.")
    public ApiPaginatedResponse<ApiProcessingOrder> getProcessingOrder(@Valid ApiPaginatedRequest request) {
        return new ApiPaginatedResponse<>(processingOrderService.getProcessingOrderList(request));
    }

    @PutMapping
    @ApiOperation("Create or update processing order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateProcessingOrder(
            @Valid @RequestBody ApiProcessingOrder apiProcessingOrder,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        return new ApiResponse<>(processingOrderService.createOrUpdateProcessingOrder(apiProcessingOrder, authUser.getUserId()));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a processing order with the provided ID.")
    public ApiDefaultResponse deleteProcessingOrder(
            @Valid @ApiParam(value = "ProcessingOrder ID", required = true) @PathVariable("id") Long id) throws ApiException {

        processingOrderService.deleteProcessingOrder(id);
        return new ApiDefaultResponse();
    }
}