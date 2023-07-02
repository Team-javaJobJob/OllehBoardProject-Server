package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.CommunityMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityMemberDTO {
    private Long id;
    private CommunityDTO community;
    private MemberDTO member;

    public static CommunityMemberDTO fromEntity(CommunityMember localCommunity) {
        return new CommunityMemberDTO(
                localCommunity.getId(),
                CommunityDTO.fromEntity(localCommunity.getCommunity()),
                MemberDTO.fromEntity(localCommunity.getMember())
        );
    }
}
