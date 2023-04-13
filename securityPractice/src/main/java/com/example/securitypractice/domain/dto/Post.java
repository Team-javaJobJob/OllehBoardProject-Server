package com.example.securitypractice.domain.dto;

import com.example.securitypractice.domain.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String body;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private int likeCount;

    public static Post formEntity(PostEntity postEntity) {

        return new Post(postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getBody(),
                User.fromEntity(postEntity.getUserEntity()),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getDeletedAt(),
                postEntity.getLikeEntityList().size());
    }
}
