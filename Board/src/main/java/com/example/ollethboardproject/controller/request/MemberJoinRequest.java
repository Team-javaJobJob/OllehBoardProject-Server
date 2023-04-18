package com.example.ollethboardproject.controller.request;

import com.example.ollethboardproject.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class MemberJoinRequest {
    private String userName;

    private String password;

    private String nickName;

    private Gender gender;

    public void encode(String encodePassword) {
        this.password = encodePassword;
    }
}
