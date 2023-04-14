package com.example.ollethboardproject.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content" , columnDefinition = "text")
    private String content;

    //TODO: 회의를 통해 ManyToOne 에 대한 fetch 타입 지정 (JPA N+1 문제)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      // 단방향 매핑 ( 양방향 매핑에 대한 근거부족으로 인한 )

}
