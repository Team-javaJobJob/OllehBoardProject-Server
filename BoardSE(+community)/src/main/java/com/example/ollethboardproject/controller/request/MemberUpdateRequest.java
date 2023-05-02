package com.example.ollethboardproject.controller.request;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateRequest {
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;
    private Role role;

}
