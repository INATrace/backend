package com.abelium.inatrace.types;

public enum BeycoCurrency {

    USD("Usd"),
    USD_CT("UsdCt"),
    EUR("Eur");

    private final String currency;

    BeycoCurrency(String s) {
        this.currency = s;
    }

    @Override
    public String toString() {
        return this.currency;
    }

}
