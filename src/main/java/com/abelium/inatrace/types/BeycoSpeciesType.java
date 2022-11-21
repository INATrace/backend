package com.abelium.inatrace.types;

public enum BeycoSpeciesType {

    ARABICA("Arabica"),
    ROBUSTA("Robusta");

    private final String speciesType;

    BeycoSpeciesType(String s) {
        this.speciesType = s;
    }

    @Override
    public String toString() {
        return this.speciesType;
    }

}
