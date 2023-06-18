package com.example.ollethboardproject.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    private Keyword(String keyword, Community community) {
        this.keyword = keyword;
        this.community = community;
    }

    public static Keyword of(String keyword, Community community) {
        return new Keyword(
                keyword,
                community
        );
    }
}
