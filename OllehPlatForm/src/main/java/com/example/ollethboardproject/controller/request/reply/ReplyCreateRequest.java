package com.example.ollethboardproject.controller.request.reply;

import com.example.ollethboardproject.domain.entity.Member;

public class ReplyCreateRequest {
    private Long postId;
    private Long commentId;
    private String content;


    public Long getPostId() {
        return postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }


}