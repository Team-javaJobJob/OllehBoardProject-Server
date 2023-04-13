package com.example.securitypractice.domain.dto;

import com.example.securitypractice.domain.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {
    private String comment;

    public static Comment fromEntity(CommentEntity commentEntity){
        return new Comment(commentEntity.getComment());
    }
}
