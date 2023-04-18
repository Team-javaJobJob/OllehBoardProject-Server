package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class MemberDTO implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private Gender gender;

    public static MemberDTO fromEntity(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getUserName(),
                member.getPassword(),
                member.getNickName(),
                member.getGender()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
