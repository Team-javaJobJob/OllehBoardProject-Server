package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberCacheRepository {
    private final RedisTemplate<String, Member> memberRedisTemplate;

    private final static Duration MEMBER_CACHE_TTL = Duration.ofDays(1);

    public void setMember(Member member) {
        String key = getKey(member.getUsername());
        log.info("Set Member to Redis {}({})", key, member);
        memberRedisTemplate.opsForValue().set(key,member,MEMBER_CACHE_TTL);
    }

    public Optional<Member> getMember(String userName) {
        Member data = memberRedisTemplate.opsForValue().get(getKey(userName));
        log.info("Get Member from Redis {}", data);
        return Optional.ofNullable(data);
    }

    private String getKey(String userName) {
        return "UID:" + userName;
    }

}
