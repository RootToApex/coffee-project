package com.coffee.domain.menu.service;

import com.coffee.domain.menu.dto.MenuResponse;
import com.coffee.domain.menu.dto.PopularMenuResponse;
import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.repository.MenuRepository;
import com.coffee.domain.order.service.OrderRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderRankingService orderRankingService;

    public List<MenuResponse> getAllMenus() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponse::from)
                .toList();
    }

    // 최근 7일 인기 메뉴 TOP 3
    public List<PopularMenuResponse> getPopularMenus() {
        List<Map.Entry<Long, Long>> ranking = orderRankingService.getPopularMenuIds(7, 3);

        if (ranking.isEmpty()) {
            return List.of();
        }

        // menuId 리스트로 한 번에 메뉴 정보 조회 (N+1 방지)
        List<Long> menuIds = ranking.stream()
                .map(Map.Entry::getKey)
                .toList();

        Map<Long, Menu> menuMap = menuRepository.findAllById(menuIds).stream()
                .collect(Collectors.toMap(Menu::getId, Function.identity()));

        // 랭킹 순서 유지하면서 응답 만들기
        return ranking.stream()
                .map(entry -> {
                    Menu menu = menuMap.get(entry.getKey());
                    if (menu == null) return null;  // 메뉴가 삭제된 경우
                    return new PopularMenuResponse(
                            menu.getId(),
                            menu.getName(),
                            menu.getPrice(),
                            entry.getValue()
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }
}