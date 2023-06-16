package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.CommunityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OllehLogResponse {
    private Long id;
    private String communityName;
    private String createdAt;

    public static OllehLogResponse fromDTO(CommunityDTO communityDTO) {
        return new OllehLogResponse(
                communityDTO.getId(),
                communityDTO.getCommunityName(),
                communityDTO.getCreatedAt()
        );
    }
}
