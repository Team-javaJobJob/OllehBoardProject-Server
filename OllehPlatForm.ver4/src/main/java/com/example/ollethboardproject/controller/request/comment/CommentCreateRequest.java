package com.example.ollethboardproject.controller.request.comment;

public class CommentCreateRequest {
    private Long postId;
    private String content;

    public Long getPostId() {
        return postId;
    }
    public String getContent() {
        return content;
    }
}