package com.abelium.inatrace.types;

public enum BeycoIncoterms {

    TBD("Tbd"),
    FOB("Fob"),
    FOT("Fot"),
    FCA("Fca"),
    FAS("Fas"),
    EXW("Exw"),
    CFR("Cfr"),
    CIF("Cif"),
    CPT("Cpt"),
    CIP("Cip"),
    DAP("Dap"),
    DPU("Dpu"),
    DDP("Ddp"),
    OTHER("Other");

    private final String incoterms;

    BeycoIncoterms(String s) {
        this.incoterms = s;
    }

    @Override
    public String toString() {
        return this.incoterms;
    }

}
