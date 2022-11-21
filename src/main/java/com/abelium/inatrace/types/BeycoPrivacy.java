package com.abelium.inatrace.types;

public enum BeycoPrivacy {
    PUBLIC("Public"),
    USERS("Users"),
    CONNECTIONS("Connections"),
    LIST("List"),
    PRIVATE("Private"),
    ALL("All");

    private final String privacy;

    BeycoPrivacy(String s) {
        this.privacy = s;
    }

    @Override
    public String toString() {
        return this.privacy;
    }
}
