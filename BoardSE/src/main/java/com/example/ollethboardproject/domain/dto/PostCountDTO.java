package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.PostCount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCountDTO {
    private Integer count;
    private String title;
    private String content;

    public static PostCountDTO of(Integer countByBoard, PostCount postCount) {
        return new PostCountDTO(countByBoard, postCount.getPost().getTitle(), postCount.getPost().getContent());
    }
}
