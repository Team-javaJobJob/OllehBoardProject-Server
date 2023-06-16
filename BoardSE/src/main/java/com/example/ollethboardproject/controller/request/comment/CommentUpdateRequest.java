package com.example.ollethboardproject.controller.request.comment;

import lombok.Getter;


@Getter
public class CommentUpdateRequest {
        //TODO: content 값 넣기
    private String content;
    public String getContent() {
        return content;
    }
}