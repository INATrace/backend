package com.abelium.inatrace.types;

public enum BeycoCoffeeUnit {

    KG("Kg"),
    MG("Mg"),
    BAG_25("Bag25"),
    BAG_30("Bag30"),
    BAG_35("Bag35"),
    BAG_46("Bag46"),
    BAG_50("Bag50"),
    BAG_59("Bag59"),
    BAG_60("Bag60"),
    BAG_69("Bag69"),
    BAG_70("Bag70"),
    BAG_1000("Bag1000");

    private final String unit;

    BeycoCoffeeUnit(String s) {
        this.unit = s;
    }

    @Override
    public String toString() {
        return this.unit;
    }

}
