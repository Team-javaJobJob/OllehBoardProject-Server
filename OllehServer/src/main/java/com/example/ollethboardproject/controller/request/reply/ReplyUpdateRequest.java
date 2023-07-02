package com.example.ollethboardproject.controller.request.reply;

import lombok.Getter;


@Getter
public class ReplyUpdateRequest {
    private String content;

    public String getContent() {
        return content;
    }
}