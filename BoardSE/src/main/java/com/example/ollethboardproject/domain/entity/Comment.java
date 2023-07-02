
        package com.example.ollethboardproject.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;



import javax.persistence .*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "comments")
    @Getter
    @NoArgsConstructor
    public class Comment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String content;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        private Post post;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        private Member member;

        @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
        private List<Reply> replies = new ArrayList<>();

        @Column(nullable = false)
        private LocalDateTime createdAt;

        public Comment(String content, Post post, Member member) {
            this.id = null;
            this.content = content;
            this.post = post;
            this.member = member;
            this.createdAt = LocalDateTime.now();
        }

        public List<Reply> getReplies() {
            return replies;
        }




    public static Comment of(String content, Post post, Member member){
        return new Comment(content,post,member);
    }


    public void update(String content) {
        this.content = content;
    }
}
