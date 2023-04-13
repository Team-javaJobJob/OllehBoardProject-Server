package com.example.securitypractice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
}
