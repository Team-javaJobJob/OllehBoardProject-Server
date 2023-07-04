package com.example.ollethboardproject.utils;

import lombok.Getter;

@Getter
public class TokenInfo {
    public String accessToken;
    public String refreshToken;

    private TokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenInfo generateTokens(String accessToken, String refreshToken) {
        return new TokenInfo(
                accessToken,
                refreshToken
        );
    }
}
