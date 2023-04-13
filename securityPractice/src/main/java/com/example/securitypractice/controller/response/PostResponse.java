package com.example.securitypractice.controller.response;

import com.example.securitypractice.domain.dto.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime updatedAt;
    private int likeCount;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getUser().getUsername(),
                post.getUpdatedAt(),
                post.getLikeCount());
    }
}
