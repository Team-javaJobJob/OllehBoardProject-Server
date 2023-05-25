package com.example.ollethboardproject.controller.request.reply;

import com.example.ollethboardproject.domain.entity.Member;

public class ReplyCreateRequest {
    private Long postId;
    private Long commentId;
    private Long replyId;
    private String content;
    private Member author;
    public Long getPostId() {
        return postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public Member getAuthor() {
        return author;
    }

}