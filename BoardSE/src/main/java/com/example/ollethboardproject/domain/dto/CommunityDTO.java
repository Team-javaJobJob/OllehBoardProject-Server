package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityDTO {
    private Long id;
    private String region;
    private String interest;
    private String info;
    private String communityName;
    private Member member;

    public static CommunityDTO fromEntity(Community community) {
        return new CommunityDTO(
                community.getId(),
                community.getRegion(),
                community.getInterest(),
                community.getInfo(),
                community.getCommunityName(),
                community.getMember());
    }

}
