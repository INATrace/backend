package com.abelium.inatrace.components.beycoorder;

import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.beycoorder.api.ApiBeycoOrderFields;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chain/beyco-order")
public class BeycoOrderController {

    private final BeycoOrderService beycoOrderService;

    @Autowired
    public BeycoOrderController(
            BeycoOrderService beycoOrderService
    ) {
        this.beycoOrderService = beycoOrderService;
    }

    @GetMapping("/fields")
    @ApiOperation("Get list of fields necessary for Beyco order for selected Stock Orders")
    public ApiResponse<ApiBeycoOrderFields> getBeycoOrderFieldsForSelectedStockOrders(
            @RequestParam(value = "id") List<Long> stockOrderIds
    ) throws ApiException {
        return new ApiResponse<>(beycoOrderService.getBeycoOrderFieldList(stockOrderIds));
    }

}
