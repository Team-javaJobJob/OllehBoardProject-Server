package com.example.ollethboardproject.controller.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequest {
    private String userName;
    private String password;
}
