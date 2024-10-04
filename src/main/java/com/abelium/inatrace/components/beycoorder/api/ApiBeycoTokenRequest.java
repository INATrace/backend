package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.TokenGrantType;
import io.swagger.v3.oas.annotations.media.Schema;

public class ApiBeycoTokenRequest {

    @Schema(description = "Type of grant")
    private TokenGrantType grantType;

    @Schema(description = "ID of client application")
    private String clientId;

    @Schema(description = "Belonging secret of client application")
    private String clientSecret;

    @Schema(description = "Authorization code of a user")
    private String code;

    @Schema(description = "Refresh token")
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
