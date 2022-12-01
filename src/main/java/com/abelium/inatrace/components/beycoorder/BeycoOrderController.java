package com.abelium.inatrace.components.beycoorder;

import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.beycoorder.api.ApiBeycoOrderFields;
import com.abelium.inatrace.components.beycoorder.api.ApiBeycoTokenResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/company/{companyId}/token")
    @ApiOperation("Get OAuth2 token for Beyco integration")
    public ApiResponse<ApiBeycoTokenResponse> getToken(
            @ApiParam(value = "Authorization code from Beyco OAuth2", required = true) @RequestParam(value = "authCode") String authCode,
            @ApiParam(value = "ID of company", required = true) @PathVariable(value = "companyId") Long companyId
    ) throws ApiException {
        return new ApiResponse<>(beycoOrderService.getBeycoAuthToken(authCode, companyId));
    }

    @GetMapping("/company/{companyId}/token/refresh")
    @ApiOperation("Refresh expired token")
    public ApiResponse<ApiBeycoTokenResponse> refreshToken(
            @ApiParam(value = "Refresh token", required = true) @RequestHeader(value = "X-Beyco-Refresh-Token") String refreshToken,
            @ApiParam(value = "ID of company", required = true) @PathVariable(value = "companyId") Long companyId
    ) throws ApiException {
        return new ApiResponse<>(beycoOrderService.refreshBeycoAuthToken(refreshToken, companyId));
    }

    @GetMapping("/company/{companyId}/fields")
    @ApiOperation("Get list of fields necessary for Beyco order for selected Stock Orders")
    public ApiResponse<ApiBeycoOrderFields> getBeycoOrderFieldsForSelectedStockOrders(
            @ApiParam(value = "ID's of selected stock orders", required = true) @RequestParam(value = "id") List<Long> stockOrderIds,
            @ApiParam(value = "ID of company", required = true) @PathVariable(value = "companyId") Long companyId
    ) throws ApiException {
        return new ApiResponse<>(beycoOrderService.getBeycoOrderFieldList(stockOrderIds, companyId));
    }

    @PostMapping("/company/{companyId}/order")
    @ApiOperation("Send order to Beyco")
    public ApiResponse<Object> sendBeycoOrder(
            @Valid @ApiParam(value = "Beyco offer", required = true) @RequestBody ApiBeycoOrderFields beycoOrder,
            @ApiParam(value = "JWT token", required = true) @RequestHeader(value = "X-Beyco-Token") String token,
            @ApiParam(value = "ID of company", required = true) @PathVariable(value = "companyId") Long companyId
    ) throws ApiException {
        return new ApiResponse<>(this.beycoOrderService.sendBeycoOrder(beycoOrder, token, companyId));
    }

}
