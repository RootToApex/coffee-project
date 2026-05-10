package com.coffee.domain.order.service;

import com.coffee.common.exception.CustomException;
import com.coffee.common.exception.ErrorCode;
import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.repository.MenuRepository;
import com.coffee.domain.order.dto.OrderRequest;
import com.coffee.domain.order.dto.OrderResponse;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.event.OrderCreatedEvent;
import com.coffee.domain.order.event.OrderEventPublisher;
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
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public OrderResponse order(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 조건부 업데이트로 포인트 차감, 잔액 부족 시 0반환 예외 처리
        int updated = userRepository.deductPoint(user.getId(), menu.getPrice());
        if (updated == 0) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINT);
        }

        // 주문 시점 가격 저장(메뉴 가격 변경 시 주문 내역 보존)
        Order order = Order.create(user.getId(), menu.getId(), menu.getPrice());
        Order saved = orderRepository.save(order);

        // 스프링 이벤트로 발행, 실제 Kafka 전송은 트랜잭션 커밋 후
        orderEventPublisher.publishOrderCreated(
                new OrderCreatedEvent(
                        saved.getId(),
                        saved.getUserId(),
                        saved.getMenuId(),
                        saved.getPrice(),
                        saved.getCreatedAt()
                )
        );
        return OrderResponse.from(saved);
    }
}