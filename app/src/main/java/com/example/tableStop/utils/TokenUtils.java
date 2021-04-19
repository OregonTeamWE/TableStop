package com.example.tableStop.utils;

import android.net.Uri;

import com.example.tableStop.dataClass.TokenInfo;
import com.google.gson.Gson;

public class TokenUtils {
    private final static String EBAY_TOKEN_URL = "https://api.ebay.com/identity/v1/oauth2/token";

    public static String buildTokenURL() {
        return Uri.parse(EBAY_TOKEN_URL).buildUpon().toString();
    }

    static class EBAYToken {
        String access_token;
        int expires_in;
    }

    public static TokenInfo parseTokenJSON(String TokenJSON) {
        Gson gson = new Gson();
        EBAYToken result = gson.fromJson(TokenJSON, EBAYToken.class);
        if (result != null) {
            TokenInfo tokenInfo = new TokenInfo();

            tokenInfo.access_token = result.access_token;
            tokenInfo.expires_in = result.expires_in;

            return tokenInfo;
        } else
            return null;
    }
}