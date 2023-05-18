package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.LocalCommunityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocalCommunityResponse {
    private String nickName;

    public static LocalCommunityResponse fromLocalCommunityDTO(LocalCommunityDTO localCommunityDTO){
        return new LocalCommunityResponse(localCommunityDTO.getMember().getNickName());
    }
}
