package com.example.ollethboardproject.controller.request.comment;

import lombok.Getter;


@Getter
public class CommentUpdateRequest {

    private String content;
    public String getContent() {
        return content;
    }
}