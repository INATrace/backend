package com.abelium.inatrace.components.currencies;

import com.abelium.inatrace.api.errors.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/chain/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(path = "/convert/{value}/{from}/to/{to}")
    @Operation(summary = "Convert a value between supported currencies")
    public BigDecimal convert(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("value") BigDecimal value) throws ApiException {
        return currencyService.convert(from, to, value);
    }

    @GetMapping(path = "/convert/{value}/{from}/to/{to}/on/{date}")
    @Operation(summary = "Convert a value between supported currencies at the specified date")
    public BigDecimal convertAtDate(
            @PathVariable("value") BigDecimal value,
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return currencyService.convertAtDate(from, to, value, date);
    }

}
