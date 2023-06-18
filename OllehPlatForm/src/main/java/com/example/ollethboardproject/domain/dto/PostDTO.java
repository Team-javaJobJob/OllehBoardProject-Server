package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Post;
import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Member member;
    private String createdAt;

    public static PostDTO fromEntity(Post post) {
        return new PostDTO(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember(),
                post.getCreatedAt()
        );
    }
}
