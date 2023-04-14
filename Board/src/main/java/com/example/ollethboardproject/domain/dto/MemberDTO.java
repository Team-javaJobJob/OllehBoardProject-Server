package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;
}
