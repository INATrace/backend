package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.currencies.api.ApiCurrencyTypeRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chain/currency-type")
public class CurrencyTypeController {

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @GetMapping("list")
    @ApiOperation(value = "Get list of enabled and disables supported currencies")
    public ApiPaginatedResponse<ApiCurrencyType> getCurrencyTypes(ApiCurrencyTypeRequest request) {
        return currencyTypeService.getCurrencyTypeList(null, request);
    }

    @GetMapping("list/enabled")
    @ApiOperation(value = "Get list of enabled supported currencies")
    public ApiPaginatedResponse<ApiCurrencyType> getEnabledCurrencyTypes(ApiCurrencyTypeRequest request) {
        return currencyTypeService.getCurrencyTypeList(Boolean.TRUE, request);
    }

    @GetMapping("list/disabled")
    @ApiOperation(value = "Get list of disabled supported currencies")
    public ApiPaginatedResponse<ApiCurrencyType> getDisabledCurrencyTypes(ApiCurrencyTypeRequest request) {
        return currencyTypeService.getCurrencyTypeList(Boolean.FALSE, request);
    }

    @PutMapping("{id}/enable")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Enable currency with the specified ID")
    public ApiDefaultResponse enableCurrency(@PathVariable("id") Long id) {
        currencyTypeService.updateStatus(id, Boolean.TRUE);
        return new ApiDefaultResponse();
    }

    @PutMapping("{id}/disable")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Disable currency with the specified ID")
    public ApiDefaultResponse disableCurrency(@PathVariable("id") Long id) {
        currencyTypeService.updateStatus(id, Boolean.FALSE);
        return new ApiDefaultResponse();
    }
}
