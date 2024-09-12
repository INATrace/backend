package com.abelium.inatrace.components.beycoorder.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ApiBeycoTokenResponse {

    @Schema(description = "JWT token for authentication")
    private String accessToken;

    @Schema(description = "Type of token")
    private String tokenType;

    @Schema(description = "Remaining time of token's life")
    private Long expiresIn;

    @Schema(description = "Refresh token")
    private String refreshToken;

    @Schema(description = "Scope of the token")
    private List<String> scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
