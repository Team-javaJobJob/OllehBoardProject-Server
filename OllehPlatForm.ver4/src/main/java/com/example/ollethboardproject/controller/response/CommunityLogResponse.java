package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.CommunityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLogResponse {
    private String communityName;

    public static CommunityLogResponse fromDTO(CommunityDTO communityDTO) {
        return new CommunityLogResponse(
                communityDTO.getCommunityName()
        );
    }
}
