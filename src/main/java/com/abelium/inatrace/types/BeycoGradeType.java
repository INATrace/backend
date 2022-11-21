package com.abelium.inatrace.types;

public enum BeycoGradeType {

    NA("Na"),
    GRADE1("Grade1"),
    GRADE2("Grade2"),
    GRADE3("Grade3"),
    GRADE4("Grade4"),
    GRADE5("Grade5"),
    AA("Aa"),
    A("A"),
    AB("Ab"),
    B("B"),
    C("C"),
    PB("Pb"),
    X("X"),
    E("E"),
    PSC("Psc"),
    Y1("Y1"),
    Y2("Y2"),
    T("T"),
    HG_HB("HgHb"),
    SHG_SHB("ShgShb"),
    OTHER("Other");

    private final String grade;

    BeycoGradeType(String s) {
        this.grade = s;
    }

    @Override
    public String toString() {
        return this.grade;
    }

}
