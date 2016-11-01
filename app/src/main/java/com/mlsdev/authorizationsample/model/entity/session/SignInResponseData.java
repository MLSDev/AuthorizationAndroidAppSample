package com.mlsdev.authorizationsample.model.entity.session;

import com.google.gson.annotations.SerializedName;

public class SignInResponseData {
    @SerializedName("auth_token")
    private String authToken;
    @SerializedName("expiration_date")
    private String expirationDate;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

}
