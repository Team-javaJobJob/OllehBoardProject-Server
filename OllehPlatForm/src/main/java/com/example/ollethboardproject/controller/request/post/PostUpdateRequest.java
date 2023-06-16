package com.example.ollethboardproject.controller.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdateRequest {
    private String title;
    private String content;
}
