package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private Long postId;
    private String memberName;


    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getMember().getNickName()
        );
    }
}