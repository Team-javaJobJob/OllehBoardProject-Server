package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLogResponse {
    private String title;
    private LocalDateTime createdAt;
    private String nickName;

    public static PostLogResponse fromDTO(PostDTO postDTO) {
        return new PostLogResponse(
                postDTO.getTitle(),
                postDTO.getCreatedAt(),
                postDTO.getMember().getNickName()
        );
    }
}
