package com.example.ollethboardproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequest {
    private String userName;
    private String password;
}
