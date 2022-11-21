package com.abelium.inatrace.types;

public enum BeycoVarietyType {

    BOURBON("Bourbon"),
    CASTILLO("Castillo"),
    CATIMOR("Catimor"),
    CATUAI("Catuai"),
    CATURRA("Caturra"),
    GEISHA("Geisha"),
    HEIRLOOM("Heirloom"),
    IH90("Ih90"),
    JACKSON("Jackson"),
    JAVA("Java"),
    LEMPIRA("Lempira"),
    MUNDONOVO("MundoNovo"),
    SL14("Sl14"),
    SL28("Sl28"),
    SL34("Sl34"),
    TYPICA("Typica"),
    OTHER("Other");

    private final String variety;

    BeycoVarietyType(String s) {
        this.variety = s;
    }

    @Override
    public String toString() {
        return this.variety;
    }

}
