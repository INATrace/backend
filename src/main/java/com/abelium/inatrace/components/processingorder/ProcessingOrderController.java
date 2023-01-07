package com.abelium.inatrace.components.processingorder;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingorder.api.ApiProcessingOrder;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
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
            @Valid @ApiParam(value = "ProcessingOrder ID", required = true) @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiResponse<>(processingOrderService.getProcessingOrder(id, authUser, language));
    }

    @PutMapping
    @ApiOperation("Create or update processing order. If the ID is provided, then the entity with the provided ID is updated.")
    public ApiResponse<ApiBaseEntity> createOrUpdateProcessingOrder(
            @Valid @RequestBody ApiProcessingOrder apiProcessingOrder,
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

        return new ApiResponse<>(processingOrderService.createOrUpdateProcessingOrder(apiProcessingOrder, authUser, language));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a processing order with the provided ID.")
    public ApiDefaultResponse deleteProcessingOrder(
            @Valid @ApiParam(value = "ProcessingOrder ID", required = true) @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {

        processingOrderService.deleteProcessingOrder(id, authUser);
        return new ApiDefaultResponse();
    }

}
