package com.coffee.domain.order.service;

import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.repository.MenuRepository;
import com.coffee.domain.order.dto.OrderRequest;
import com.coffee.domain.order.dto.OrderResponse;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.repository.OrderRepository;
import com.coffee.domain.user.entity.User;
import com.coffee.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderRankingService orderRankingService;

    @Transactional
    public OrderResponse order(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

        user.use(menu.getPrice());

        Order order = Order.create(user.getId(), menu.getId(), menu.getPrice());
        Order saved = orderRepository.save(order);

        orderRankingService.recordOrder(saved.getId(), saved.getMenuId(), saved.getCreatedAt());

        return OrderResponse.from(saved);
    }
}