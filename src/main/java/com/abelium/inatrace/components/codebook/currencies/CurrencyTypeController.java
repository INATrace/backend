package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
