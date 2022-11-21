package com.abelium.inatrace.types;

public enum BeycoProcessType {

    FULLY_WASHED("FullyWashed"),
    SEMI_WASHED_HONEY("SemiWashedHoney"),
    NATURAL("Natural"),
    OTHER("Other");

    private final String process;

    BeycoProcessType(String s) {
        this.process = s;
    }

    @Override
    public String toString() {
        return this.process;
    }

}
