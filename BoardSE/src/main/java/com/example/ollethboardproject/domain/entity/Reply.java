
package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.request.reply.ReplyCreateRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    public static Reply of(ReplyCreateRequest request, Member member, Post post, Comment parentComment) {
        Reply reply = new Reply();
        reply.setContent(request.getContent());
        reply.setMember(member);
        reply.setPost(post);
        reply.setParentComment(parentComment);
        return reply;
    }

    public void update(String content) {
        this.setContent(content);
    }

    public void setAuthor(Member author) {
        this.member = author;
    }
}