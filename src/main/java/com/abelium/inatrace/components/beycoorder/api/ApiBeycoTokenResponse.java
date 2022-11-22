package com.abelium.inatrace.components.beycoorder.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ApiBeycoTokenResponse {

    @ApiModelProperty(value = "JWT token for authentication")
    private String accessToken;

    @ApiModelProperty(value = "Type of token")
    private String tokenType;

    @ApiModelProperty(value = "Remaining time of token's life")
    private Long expiresIn;

    @ApiModelProperty(value = "Refresh token")
    private String refreshToken;

    @ApiModelProperty(value = "Scope of the token")
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
