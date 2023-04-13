package com.example.securitypractice.controller.response;

import com.example.securitypractice.domain.dto.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private String comment;

    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(comment.getComment());
    }
}
