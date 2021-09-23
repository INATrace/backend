package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chain/currency-type")
public class CurrencyTypeController {

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @GetMapping("list")
    @ApiOperation(value = "Get list of supported currencies")
    public List<ApiCurrencyType> getCurrencyTypes() {
        return currencyTypeService.getCurrencyTypeList();
    }

    @GetMapping("list/enabled")
    @ApiOperation(value = "Get list of enabled supported currencies")
    public List<ApiCurrencyType> getEnabledCurrencyTypes() {
        return currencyTypeService.getEnabledCurrencyTypeList();
    }

    @GetMapping("list/disabled")
    @ApiOperation(value = "Get list of disabled supported currencies")
    public List<ApiCurrencyType> getDisabledCurrencyTypes() {
        return currencyTypeService.getDisabledCurrencyTypeList();
    }

    @PutMapping("{id}/enable")
    @ApiOperation(value = "Enable currency with the specified ID")
    public ApiDefaultResponse enableCurrency(@PathVariable("id") Long id) {
        currencyTypeService.updateStatus(id, Boolean.TRUE);
        return new ApiDefaultResponse();
    }

    @PutMapping("{id}/disable")
    @ApiOperation(value = "Disable currency with the specified ID")
    public ApiDefaultResponse disableCurrency(@PathVariable("id") Long id) {
        currencyTypeService.updateStatus(id, Boolean.FALSE);
        return new ApiDefaultResponse();
    }
}
