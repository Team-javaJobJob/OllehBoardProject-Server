package com.example.ollethboardproject.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "olleh")
public class Olleh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key 자동생성
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //외래키(foreign key) 지정
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

//    @Column(nullable = false) //이 필드는 null 값을 가질 수 없음
//    private boolean status = true; //사용자가 좋아요를 눌렀는지 여부

    private Olleh(Member member, Community community) {
        this.member = member;
        this.community = community;
    } //생성자 인자로 Member 객체와 Community 객체를 받아서, Olleh 객체 생성, 이때 status 필드 값은 true(좋아요가 눌렸다면 true)

    public static Olleh of(Member member, Community community) { //Member 객체와 Community 객체를 받아서 Olleh 객체를 생성하는 정적 메소드
        return new Olleh(member, community);
    }
}


