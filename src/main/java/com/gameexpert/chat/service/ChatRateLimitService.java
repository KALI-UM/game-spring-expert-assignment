package com.gameexpert.chat.service;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRateLimitService {

    private final StringRedisTemplate redisTemplate;

    //없는 키에는 TTL이 안 걸리기 때문에 증가 후 TTL 설정
    private static DefaultRedisScript<Boolean> INCR_SCRIPT = new DefaultRedisScript<Boolean>("""
            local limit = tonumber(ARGV[1])
            local count = tonumber(redis.call('GET', KEYS[1]) or '0')
            if count >= limit then
                return false
            end
             redis.call('INCR', KEYS[1])
            
             if(count==0) then
            redis.call('EXPIRE', KEYS[1], 10)
           end
            return true
            """, Boolean.class);

    public boolean allow(Long playerId) {
        String key = "chat:limit:" + playerId;

        //중복검사니까 제외
//        String value = redisTemplate.opsForValue().get(key);
//        int count = value == null ? 0 : Integer.parseInt(value);
//        if (count >= 5) {
//            return false;
//        }
        // TODO Lv 19: 횟수 확인부터 최초 만료 설정까지 원자적으로 실행합니다.
        Boolean updated = redisTemplate.execute(INCR_SCRIPT, List.of(key),
                String.valueOf(5));
        return Boolean.TRUE.equals(updated);
    }
}
