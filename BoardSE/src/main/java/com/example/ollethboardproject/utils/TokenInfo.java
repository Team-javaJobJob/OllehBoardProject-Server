package com.example.ollethboardproject.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {
    public String accessToken;

    public static TokenInfo generateTokens(String accessToken) {
        return new TokenInfo(accessToken);
    }
}
