<<<<<<< HEAD
package com.example.ollethboardproject.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Post post;


    private PostCount(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public static PostCount of(Member member, Post post) {
        return new PostCount(member, post);
    }
}
=======
package com.example.ollethboardproject.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Post post;


    private PostCount(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public static PostCount of(Member member, Post post) {
        return new PostCount(member, post);
    }
}
>>>>>>> main
