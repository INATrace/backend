package com.abelium.inatrace.components.groupstockorder;

import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.components.groupstockorder.api.ApiGroupStockOrder;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/chain/group-stock-order")
public class GroupStockOrderController {

    private final GroupStockOrderService groupStockOrderService;

    @Autowired
    public GroupStockOrderController(GroupStockOrderService groupStockOrderService) {
        this.groupStockOrderService = groupStockOrderService;
    }

    @GetMapping("/list/facility/{facilityId}")
    @ApiOperation("Get a paginated list of grouped stock orders.")
    public ApiPaginatedResponse<ApiGroupStockOrder> getGroupedStockOrderList(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable Long facilityId,
            @Valid @ApiParam(value = "Available orders only") @RequestParam(value = "availableOnly", required = false) Boolean availableOnly,
            @Valid @ApiParam(value = "Is purchase orders only") @RequestParam(value = "isPurchaseOrderOnly", required = false) Boolean isPurchaseOrderOnly,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) {
        return new ApiPaginatedResponse<>(this.groupStockOrderService.getGroupedStockOrderList(
                request,
                new GroupStockOrderQueryRequest(
                        facilityId,
                        availableOnly,
                        isPurchaseOrderOnly,
                        semiProductId
                ),
                language
        ));
    }

}
