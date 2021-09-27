package com.abelium.inatrace.components.currencies.api;

import java.util.Map;

public class ApiCurrencySymbolsResponse {

    private boolean success;
    private Map<String, String> symbols;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}
