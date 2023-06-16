package com.example.ollethboardproject.controller.request.member;

import com.example.ollethboardproject.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequest {
    private String requestPw;
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;

    public void encode(String encodePassword) {
        this.password = encodePassword;
    }

}
