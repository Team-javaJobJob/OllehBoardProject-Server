package com.example.ollethboardproject.controller.request.member;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequest {
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;

    public void encode(String encodePassword) {
        this.password = encodePassword;
    }
}
