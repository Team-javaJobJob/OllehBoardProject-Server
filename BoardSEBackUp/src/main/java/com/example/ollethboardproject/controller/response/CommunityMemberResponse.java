package com.example.ollethboardproject.controller.response;



import com.example.ollethboardproject.domain.dto.CommunityMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommunityMemberResponse {
    private String nickName;

    public static CommunityMemberResponse fromCommunityMemberDTO(CommunityMemberDTO localCommunityDTO){
        return new CommunityMemberResponse(localCommunityDTO.getMember().getNickName());
    }
}
