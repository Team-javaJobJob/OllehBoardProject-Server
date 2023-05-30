package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.request.post.PostCreateRequest;
import com.example.ollethboardproject.controller.request.post.PostUpdateRequest;
import com.example.ollethboardproject.domain.entity.audit.AuditEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    //TODO: 회의를 통해 ManyToOne 에 대한 fetch 타입 지정 (JPA N+1 문제)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;     // 단방향 매핑 ( 양방향 매핑에 대한 근거부족으로 인한 )

    private Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Post of(PostCreateRequest postCreateRequest, Member member) {
        return new Post(postCreateRequest.getTitle(), postCreateRequest.getContent(), member);
    }

    public void update(PostUpdateRequest postUpdateRequest, Member member) {
        this.title = postUpdateRequest.getTitle();
        this.content = postUpdateRequest.getContent();
        this.member = member;
    }


}