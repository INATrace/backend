package com.abelium.inatrace.types;

public enum BeycoCertificateType {

    RA("Ra"),
    FAIRTRADE("Fairtrade"),
    ORGANIC("Organic"),
    PRACTICES("Practices"),
    FOUR_C("FourC");

    private final String certificate;

    BeycoCertificateType(String s) {
        this.certificate = s;
    }

    @Override
    public String toString() {
        return this.certificate;
    }

}
