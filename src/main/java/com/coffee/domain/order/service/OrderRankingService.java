package com.coffee.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderRankingService {

    private static final String RANKING_KEY = "order:ranking";

    private final StringRedisTemplate redisTemplate;

    // 주문 발생 시 ZSET에 기록
    //  epoch milli -> ZSET의 score는 숫자만 가능하기 때문에 시간 정보를 숫자로 변환하여 저장
    public void recordOrder(Long orderId, Long menuId, LocalDateTime orderedAt) {
        String member = orderId + ":" + menuId;
        long score = orderedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.opsForZSet().add(RANKING_KEY, member, score);
    }

    // 최근 n일 인기 메뉴 id 카운트 조회
    public List<Map.Entry<Long, Long>> getPopularMenuIds(int days, int limit) {
        long now = System.currentTimeMillis();
        long from = now - (long) days * 24 * 60 * 60 * 1000;

        Set<String> members = redisTemplate.opsForZSet()
                .rangeByScore(RANKING_KEY, from, now);

        if (members == null || members.isEmpty()) {
            return Collections.emptyList();
        }

        // member 파싱 → menuId 추출 → 카운트
        Map<Long, Long> countByMenuId = members.stream()
                .map(m -> Long.parseLong(m.split(":")[1]))
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        // 내림차순 정렬로 상위 limit 반환
        return countByMenuId.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .toList();
    }
}