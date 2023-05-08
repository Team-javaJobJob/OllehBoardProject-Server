package com.example.ollethboardproject.controller.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
}
