package com.example.ollethboardproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor      // 이걸작성해야 포스트 작성이 view단에서 됨.
public class PostCreateRequest {
    private String title;
    private String content;
}
