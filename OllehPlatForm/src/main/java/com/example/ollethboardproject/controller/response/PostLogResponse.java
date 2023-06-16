package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLogResponse {
    private String title;
    private String createdAt;
    private String nickName;

    public static PostLogResponse fromDTO(PostDTO postDTO) {
        return new PostLogResponse(
                postDTO.getTitle(),
                postDTO.getCreatedAt(),
                postDTO.getMember().getNickName()
        );
    }
}
