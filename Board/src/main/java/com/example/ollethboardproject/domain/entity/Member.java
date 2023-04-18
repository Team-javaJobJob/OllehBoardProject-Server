package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.request.MemberJoinRequest;
import com.example.ollethboardproject.domain.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "nickName")
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    //외부에서 new 생성자로 entity 객체 만들지않게 하기 위함
    private Member(String userName, String password, String nickName, Gender gender) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
    }

    public static Member of(MemberJoinRequest memberJoinRequest) {
        return new Member(
                memberJoinRequest.getUserName(),
                memberJoinRequest.getPassword(),
                memberJoinRequest.getNickName(),
                memberJoinRequest.getGender());
    }
}
