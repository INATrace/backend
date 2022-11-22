package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.TokenGrantType;
import io.swagger.annotations.ApiModelProperty;

public class ApiBeycoTokenRequest {

    @ApiModelProperty(value = "Type of grant")
    private TokenGrantType grantType;

    @ApiModelProperty(value = "ID of client application")
    private String clientId;

    @ApiModelProperty(value = "Belonging secret of client application")
    private String clientSecret;

    @ApiModelProperty(value = "Authorization code of a user")
    private String code;

    @ApiModelProperty(value = "Refresh token")
    private String refreshToken;

    public TokenGrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(TokenGrantType grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
