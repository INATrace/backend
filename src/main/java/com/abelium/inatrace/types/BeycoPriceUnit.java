package com.abelium.inatrace.types;

public enum BeycoPriceUnit {

    MG("Mg"),
    LB("Lb"),
    KG("Kg");

    private final String priceUnit;

    BeycoPriceUnit(String s) {
        this.priceUnit = s;
    }

    @Override
    public String toString() {
        return this.priceUnit;
    }

}
