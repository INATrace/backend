package com.abelium.inatrace.types;

public enum TokenGrantType {

    AuthorizationCode("AuthorizationCode"),
    RefreshToken("RefreshToken");

    private final String grantType;

    TokenGrantType(String s) {
        this.grantType = s;
    }

    @Override
    public String toString() {
        return this.grantType;
    }
}
