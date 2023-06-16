package com.example.ollethboardproject.utils;

import com.example.ollethboardproject.domain.entity.Member;
import org.springframework.security.core.Authentication;

public class MemberUtil {

    public static Member getMemberWithAuthentication(Authentication authentication) {
        return ClassUtil.castingInstance(authentication.getPrincipal(), Member.class).get();
    }
}
