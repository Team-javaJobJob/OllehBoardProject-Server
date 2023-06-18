package com.example.ollethboardproject.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenCacheRepository {
    private final RedisTemplate<String, String> tokenRedisTemplate;
    private final static Duration Token_CACHE_TTL = Duration.ofDays(1);

    public void setToken(String accessToken, String refreshToken) {
        log.info("Set Token to Redis {}({})", accessToken, refreshToken);
        tokenRedisTemplate.opsForValue().set(getKey(accessToken), refreshToken, Token_CACHE_TTL);
    }

    public Optional<String> getToken(String accessToken) {
        String refreshToken = tokenRedisTemplate.opsForValue().get(getKey(accessToken));
        log.info("Get refreshToken from Redis {}", refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    public String getKey(String accessToken) {
        return "JWT:" + accessToken;
    }
}
