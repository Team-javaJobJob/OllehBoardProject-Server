
package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.request.reply.ReplyCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;


    private Reply(String content, Member member, Post post, Comment parentComment) {
        this.id = null;
        this.content = content;
        this.member = member;
        this.post = post;
        this.parentComment = parentComment;
    }

    public static Reply of(ReplyCreateRequest request, Member member, Post post, Comment parentComment) {
        return new Reply(
                request.getContent(),
                member,
                post,
                parentComment
        );
    }

    public Reply update(String content) {
        return new Reply(
                content,
                this.member,
                this.post,
                this.parentComment
        );
    }
}