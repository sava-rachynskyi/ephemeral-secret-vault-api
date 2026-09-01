package com.savarachynskyi.vault.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisSecretService {

    private final StringRedisTemplate redisTemplate;

    public void saveSecret(String id, String content, long ttlSeconds) {
        redisTemplate.opsForValue().set(id, content, Duration.ofSeconds(ttlSeconds));
    }

    public String getAndDeleteSecret(String id) {
        return redisTemplate.opsForValue().getAndDelete(id);
    }
}