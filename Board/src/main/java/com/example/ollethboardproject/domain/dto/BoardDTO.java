package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private Member member;
}
