
package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.request.member.MemberJoinRequest;

import com.example.ollethboardproject.controller.request.member.MemberUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member implements UserDetails {

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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role roles = Role.ROLE_USER;

    private String createdAt;
    private String updatedAt;

    @PostPersist
    private void setCreatedAt() {
        createdAt = LocalDateTime.now().toString();
    }

    @PostUpdate
    private void setUpdatedAt() {
        updatedAt = LocalDateTime.now().toString();
    }

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
                memberJoinRequest.getGender()

        );
    }

    public static Member toPw(MemberUpdateRequest memberUpdateRequest) {
        return new Member(
                memberUpdateRequest.getUserName(),
                memberUpdateRequest.getPassword(),
                memberUpdateRequest.getNickName(),
                memberUpdateRequest.getGender()
        );
    }

    public void update(Member memberUpdateRequest) {
        this.userName = memberUpdateRequest.getUsername();
        this.password = memberUpdateRequest.getPassword();
        this.nickName = memberUpdateRequest.getNickName();
        this.gender = memberUpdateRequest.getGender();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.roles.toString()));
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }
}
