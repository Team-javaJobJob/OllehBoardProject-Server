package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommunityDTO {
    private Long id;
    private String region;
    private String interest;
    private String info;
    private String communityName;
    private String memberNickName;
    private int ollehCount;
    private LocalDateTime createdAt;

    private String image;

    public static CommunityDTO fromEntity(Community community) {
        return new CommunityDTO(
                community.getId(),
                community.getRegion(),
                community.getInterest(),
                community.getInfo(),
                community.getCommunityName(),
                community.getMember().getNickName(),
                community.getOllehsList().size(),
                community.getCreatedAt(),
                community.getImage() != null ? community.getImage().getImageName() : "");
    }


}