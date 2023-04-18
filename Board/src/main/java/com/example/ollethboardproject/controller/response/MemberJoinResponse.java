package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinResponse {
    private String userName;
    private String nickName;
    private Gender gender;

    public static MemberJoinResponse fromUserDTO(MemberDTO memberDTO) {
        return new MemberJoinResponse(
                memberDTO.getUserName(),
                memberDTO.getNickName(),
                memberDTO.getGender()
        );
    }
}

