package com.example.ollethboardproject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_VIP("ROLE_VIP"),
    ROLE_VVIP("ROLE_VVIP");

    @Getter
    private final String status;

    @Override
    public String getAuthority() {
        return this.status;
    }
}
