package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.entity.Member;
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

    public static MemberDTO fromEntity(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getNickName(),
                member.getGender()
        );
    }
}
