package com.example.ollethboardproject.controller.request;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PwEncodeRequest {
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;
    private Role roles;

    public PwEncodeRequest(String userName, String password, String nickName, Gender gender, Role role) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
        this.roles = role;
    }

    public void encode(String encodePassword) {
        this.password = encodePassword;
    }
}
