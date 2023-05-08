package com.example.ollethboardproject.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalCommunity {
    //커뮤니티 참여자를 관리하기 위한 엔티티 클래스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isApprove = false;

    private LocalCommunity(Community community, Member member) {
        this.community = community;
        this.member = member;
    }

    public static LocalCommunity of(Community community, Member member) {
        return new LocalCommunity(community, member);
    }

    public LocalCommunity hasApprove() {
        this.isApprove = true;
        return this;
    }
}
