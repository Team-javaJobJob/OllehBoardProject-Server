package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.LocalCommunity;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocalCommunityDTO {
    private Long id;
    private CommunityDTO community;
    private MemberDTO member;

    public static LocalCommunityDTO fromEntity(LocalCommunity localCommunity) {
        return new LocalCommunityDTO(
                localCommunity.getId(),
                CommunityDTO.fromEntity(localCommunity.getCommunity()),
                MemberDTO.fromEntity(localCommunity.getMember())
        );
    }
}
